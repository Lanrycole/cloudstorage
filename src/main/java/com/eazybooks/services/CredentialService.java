package com.eazybooks.services;

import com.eazybooks.Mapper.CredentialMapper;
import com.eazybooks.Model.Credentials;
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
            credential.setUrl(userCredential.getUrl());
            credential.setKey(userCredential.getKey());
            credential.setPassword(encryptionService.decryptValue(userCredential.getPassword(), credential.getKey()));
            credential.setUsername(userCredential.getUsername());
            credential.setUserid(userCredential.getUserid());
            credential.setCredentialId(userCredential.getCredentialId());


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
        return credentialMapper.getCredentialById(credentialId);
    }

    /**
     * @param userId
     * @return Credentials
     * <p>
     * Gets list of credential of by user ID
     */
    public List<Credentials> getListOfCredential(Integer userId) {
        List<Credentials> listOfCredentials = new ArrayList<>();
        if (userId != null) {
            List<Credentials> val = credentialMapper.getListOfCredentials(userId);

            val.forEach(credentials -> {
                if (credentials != null) {
                    String decryptedPassword = encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
                    credentials.setPassword(decryptedPassword);
                    listOfCredentials.add(credentials);
                }
            });
        }

        return listOfCredentials;
    }

    /**
     * @param credentialId Deletes credential by Credential ID
     */
    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredentialById(credentialId);
    }


}
