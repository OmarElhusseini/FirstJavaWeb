package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.ResponseFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFileNameAvailable(String fileName, Integer userId){
        return fileMapper.getFile(fileName, userId)==null;
    }

    public int uploadNewFile(MultipartFile fileUpload, Integer userID) {
        if (fileUpload.isEmpty()){
            return 0;
        }
        try {
            return fileMapper.uploadNewFIle(new File(null,
                    fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    Long.toString(fileUpload.getSize()),userID, fileUpload.getBytes()));
        } catch (IOException e) {
            System.out.println("Something went wrong while uploading the file: "+ e.getMessage());
            return -1;
        }
    }

    public ResponseFile getResponseFile(File file){
        String base64 = Base64.getEncoder().encodeToString(file.getFile());
        String dataUrl = "data:" + file.getContentType() + ";base64," + base64;
        return new ResponseFile(file.getFileId(), file.getFileName(), dataUrl);
    }

    public List<ResponseFile> getFiles(Integer userId) throws Exception {
        List<File> files= fileMapper.getAllFiles(userId);
        if (files == null){
            throw new Exception();
        }

        return files.stream().map(this::getResponseFile).collect(Collectors.toList());
    }

    public File getFileByFileName(String fileName, Integer userId){
        return fileMapper.getFile(fileName, userId);
    }

}
