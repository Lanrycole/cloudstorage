package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
 import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File files);

    //gets a specific note
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    //gets list of notes that belongs to a specific user bases on userID
    @Select("Select * from FILES WHERE userid = #{userid}")
    List<File> getUserFilesById(Integer userid);

    //Deletes notes based on notes ID
    @Delete("delete from Files where fileid = #{fileId}")
    void deleteFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByName(String filename);
}
