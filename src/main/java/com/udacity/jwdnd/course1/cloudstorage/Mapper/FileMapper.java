package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(Files files);

    //gets a specific note
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files getFileById(Integer fileId);

    //gets list of notes that belongs to a specific user bases on userID
    @Select("Select * from FILES WHERE userid = #{userid}")
    List<Files> getUserFilesById(Integer userid);

    //Deletes notes based on notes ID
    @Delete("delete from Files where fileid = #{fileId}")
    void deleteFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} and userid = #{userid}" )
    Files getFileByName(String filename, int userid);
}
