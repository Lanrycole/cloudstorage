package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final HashService hashingService;
    private final EncryptionService encryptionService;

    /**
     * @param credentialMapper
     * @param hashingService
     * @param encryptionService
     */
    public CredentialService(CredentialMapper credentialMapper,
                             HashService hashingService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashingService = hashingService;
        this.encryptionService = encryptionService;
    }

    /**
     * @param userCredential
     * @return new credential
     */

    public int addOrUpdateCredentials(Credentials userCredential) {
        Credentials credential = credentialMapper.getCredentialById(userCredential.getCredentialId());
         //Updating existing credential if it exists
        if (credential != null) {

            String encryptedPassword = encryptionService.encryptValue(userCredential.getPassword(), credential.getKey());
            credential.setUrl(userCredential.getUrl());
            credential.setPassword(encryptedPassword);
            credential.setUsername(userCredential.getUsername());
            credential.setUserid(userCredential.getUserid());
            credential.setCredentialId(userCredential.getCredentialId());
            credential.setKey(credential.getKey());
            credentialMapper.updateCredential(credential);


        } else {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);

            String encryptedPassword = encryptionService.encryptValue(userCredential.getPassword(), encodedKey);

            credential = new Credentials(userCredential.getCredentialId(), userCredential.getUrl(),
                    userCredential.getUsername(), encryptedPassword, encodedKey, userCredential.getUserid());
            //Adding new credential

            credentialMapper.addCredential(credential);
        }
        return 1;
    }

    /**
     * @param credentialId
     * @return Gets credential by Id
     */

    public Credentials getCredential(Integer credentialId) {

        Credentials newCre = credentialMapper.getCredentialById(credentialId);

        Credentials cre = new Credentials();

        cre.setUrl(newCre.getUrl());
        cre.setUsername(newCre.getUsername());
        cre.setPassword(encryptionService.decryptValue(newCre.getPassword(), newCre.getKey()));
        cre.setUserid(newCre.getUserid());
        cre.setCredentialId(newCre.getCredentialId());
        cre.setKey(newCre.getKey());

        return cre;
    }

    /**
     * @param userId
     * @return Credentials
     * <p>
     * Gets list of credential of by user ID
     */
    public List<Credentials> getListOfCredential(Integer userId) {

        List<Credentials> newCre = credentialMapper.getListOfCredentials(userId);
        List<Credentials> cre = new ArrayList<>();
        newCre.forEach(credentials ->
        {
             credentials.setPassword(encryptionService.decryptValue(credentials.getPassword(), credentials.getKey()));
            cre.add(credentials);

        });
         return cre;
    }

    /**
     * @param credentialId Deletes credential by Credential ID
     */
    public void deleteCredential(Integer credentialId) {

        credentialMapper.deleteCredentialById(credentialId);
    }


}
