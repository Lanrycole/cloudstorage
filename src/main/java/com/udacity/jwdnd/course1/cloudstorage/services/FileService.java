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

    /**
     * @param fileMapper
     */
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * @param file
     * @param userId
     * @return
     * @throws IOException Saving new file
     */
    public int saveFile(MultipartFile file, Integer userId) throws IOException {
        Files newFile = new Files();

        String fileName = file.getOriginalFilename();

        newFile.setFilename(fileName);
        newFile.setContenttype(file.getContentType());
        newFile.setFilesize(file.getSize());
        newFile.setFiledata(file.getBytes());
        newFile.setUserid(userId);

        return fileMapper.saveFile(newFile);
    }


    /**
     * @param fileid
     * @return File Object
     * <p>
     * Getting file by ID
     */
    public Files getFileById(Integer fileid) {
        return fileMapper.getFileById(fileid);
    }

    /**
     *
     * @param fileId
     *
     * Deleting file bu ID
     */
    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }
    /**
     *
     * @param userId
     * @return List of Files
     */
    public List<Files> getUserFilesById(Integer userId) {
        return fileMapper.getUserFilesById(userId);
    }

    /**
     *
     * @param username
     * @param userId
     * @return file if it exists
     */
    public boolean isFileNameAvailable(String username, int userId) {
        return fileMapper.getFileByName(username, userId) == null;
    }


}
