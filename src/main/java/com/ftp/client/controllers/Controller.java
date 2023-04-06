package com.ftp.client.controllers;

import com.ftp.client.DTO.FileInfo;
import com.ftp.client.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping(value = {"/photos"},produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileInfo> getPhotos() {

        return service.getPhotosWithPrefix("GRP327_");
    }




}
