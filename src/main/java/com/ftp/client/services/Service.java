package com.ftp.client.services;

import com.ftp.client.DTO.FileInfo;
import com.ftp.client.repositories.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private ImgRepository repository;

    private List<FileInfo> getAllFiles(){
        List<FileInfo> result;
        try {
            result = repository.getAllFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public  List<FileInfo> getPhotosWithPrefix(String prefix){
        /** Фильтруем файлы, оставляем только те, что содержат префикс и являются jpg */
        return getAllFiles().stream().filter(e->e.getName().startsWith(prefix) && e.getName().contains(".jpg")).collect(Collectors.toList());
    }

}
