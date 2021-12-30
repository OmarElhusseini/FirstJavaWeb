package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM NOTES WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{credential.url}, #{credential.username}, #{credential.key}, #{credential.password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credential.credentialId")
    int insertCredential(Credential credential, Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{credential.url}, key = #{credential.key}, password = #{credential.password} WHERE credentialid = #{credential.credentialId}")
    public int updateCredential(Credential credential, Integer credentialId);

}