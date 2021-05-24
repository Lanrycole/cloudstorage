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

    public CredentialService(CredentialMapper credentialMapper,
                             HashService hashingService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashingService = hashingService;
        this.encryptionService = encryptionService;
    }

    public void addOrUpdateCredentials(Credentials userCredential) {
        Credentials credential = credentialMapper.
                getCredentialById(userCredential.getCredentialId());
        System.out.println(credential);
        if (credential != null) {
            credential.setCredentialId(userCredential.getCredentialId());
            credential.setUrl(userCredential.getUrl());
            credential.setUsername(userCredential.getUsername());
//            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
//            System.out.println("Setting decrypted password: " +decryptedPassword);

            credential.setPassword(userCredential.getPassword());
            credential.setUserid(userCredential.getUserid());
            credential.setCredentialId(userCredential.getCredentialId());
            credentialMapper.updateCredential(credential);
        } else {

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(userCredential.getPassword(), encodedKey);

            credential = new Credentials(userCredential.getUrl(), userCredential.getUsername(),
                    encryptedPassword, userCredential.getUserid(), userCredential.getCredentialId(), encodedKey);
            credentialMapper.addCredential(credential);
        }
    }


    public Credentials getCredential(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public List<Credentials> getListOfCredential(Integer userId) {

        List<Credentials> val = credentialMapper.getListOfCredentials(userId);
        List<Credentials> listOfCredentials = new ArrayList<>();
        val.forEach(credentials -> {

            if (credentials != null) {
                String decryptedPassword = encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
                 credentials.setPassword(decryptedPassword);
                listOfCredentials.add(credentials);
            }
        });
 
        return listOfCredentials;
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredentialById(credentialId);
    }
}
