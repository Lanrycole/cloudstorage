package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File();

        String fileName = file.getOriginalFilename();

        newFile.setFilename(fileName);
        newFile.setContenttype(file.getContentType());
        newFile.setFilesize(file.getSize());
        newFile.setFileData(file.getBytes());
        newFile.setUserid(userId);

        return fileMapper.saveFile(newFile);
    }

    public void viewFile(Integer fileId) {
        File file = fileMapper.getFileById(fileId);

        if (file != null) {
            fileMapper.getUserFilesById(fileId);
        } else {
            throw new IllegalStateException("FIle not found");
        }

    }
public File getFileById(Integer fileid){
        return fileMapper.getFileById(fileid);
}
    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }

    public List<File> getUserFilesById(Integer userId) {
        return fileMapper.getUserFilesById(userId);
    }
    public boolean isFileNameAvailable(String username) {
        return fileMapper.getFileByName(username) == null;
    }


}
