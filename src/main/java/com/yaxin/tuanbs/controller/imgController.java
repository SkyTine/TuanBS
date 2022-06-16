package com.yaxin.tuanbs.controller;

import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Yaxin
 * @date 2022/6/12 16:35
 */
@RestController
@RequestMapping("/TuanAPI")
public class imgController {

    @Autowired
    private FileService fileService;

    @PostMapping("/domain")
    public ResponseEntity<Record> domain(@RequestParam String tag, @RequestBody MultipartFile file){
        System.out.println(tag);
        System.out.println(file.getSize());
        String URL = fileService.saveSourceIMG(file);
        System.out.println(URL);
        return new ResponseEntity<>(new Record(), HttpStatus.OK);
    }

    @RequestMapping("")
    public ResponseEntity<String> running(){
        return new ResponseEntity<>("backend running!", HttpStatus.OK);
    }

    @RequestMapping("/getRootPath")
    public ResponseEntity<String> getRootPath(){
        return new ResponseEntity<>(fileService.getRootPath(), HttpStatus.OK);
    }

    @RequestMapping("/getRootURL")
    public ResponseEntity<String> getProfilePath(){
        return new ResponseEntity<>(fileService.getRootURL(), HttpStatus.OK);
    }
}