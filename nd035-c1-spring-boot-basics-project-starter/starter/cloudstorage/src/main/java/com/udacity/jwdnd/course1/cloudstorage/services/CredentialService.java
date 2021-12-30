package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credential encryptPassword(Credential credential){
//        byte[] array = new byte[16]; // length is bounded by 16
//        new Random().nextBytes(array);
//        String key = new String(array, Charset.forName("UTF-8"));
//        String key = RandomStringUtils
        String key = "abcdefghabcdefgh";
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credential decryptPassword(Credential credential){
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public int saveCredential(Credential credential, Integer userId){
        return credentialMapper.insertCredential(this.encryptPassword(credential), userId);
    }

    public List<Credential> getAllCredentials(Integer userId) throws Exception {
        List<Credential> credentials = credentialMapper.getAllCredentials(userId);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public int updateCredential(Credential credential, Integer credentialId){
        return credentialMapper.updateCredential(credential, credentialId);
    }

    public int deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }
}