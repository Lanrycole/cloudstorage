package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile file, Integer userId) throws IOException {
        Files newFile = new Files();

        String fileName = file.getOriginalFilename();

        newFile.setFilename(fileName);
        newFile.setContenttype(file.getContentType());
        newFile.setFilesize(file.getSize());
        newFile.setFileData(file.getBytes());
        newFile.setUserid(userId);

        return fileMapper.saveFile(newFile);
    }

    public void viewFile(Integer fileId) {
        Files file = fileMapper.getFileById(fileId);

        if (file != null) {
            fileMapper.getUserFilesById(fileId);
        } else {
            throw new IllegalStateException("FIle not found");
        }

    }
public Files getFileById(Integer fileid){
        return fileMapper.getFileById(fileid);
}
    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }

    public List<Files> getUserFilesById(Integer userId) {
        return fileMapper.getUserFilesById(userId);
    }
    public boolean isFileNameAvailable(String username, int userId) {
        return fileMapper.getFileByName(username, userId) == null;
    }


}
