package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username,key, password, userid ) " +
            "VALUES(#{url}, #{username},#{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credentials credentials);


    //gets a specific credential
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials getCredentialById(Integer credentialId);

    //gets list of notes that belongs to a specific user bases on userID
    @Select("Select * from CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getListOfCredentials(Integer userId);

    //Deletes notes based on notes ID
    @Delete("delete from CREDENTIALS where credentialid = #{credentialid}")
    void deleteCredentialById(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username}, password= #{password}, key=#{key}" +
            "WHERE credentialid =#{credentialId}")
    void updateCredential(Credentials credentials);

    @Select("Select * from CREDENTIALS WHERE username = #{username} and userid = #{userId}")
    Credentials getCredentialByName(String username, int userId);
}
