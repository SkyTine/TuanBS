package com.yaxin.tuanbs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yaxin.tuanbs.entity.IMG;
import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.entity.ResRecord;
import com.yaxin.tuanbs.service.DomainService;
import com.yaxin.tuanbs.service.FileService;
import com.yaxin.tuanbs.service.RecordService;
import com.yaxin.tuanbs.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author Yaxin
 * @date 2022/6/12 16:35
 */
@RestController
@RequestMapping("/TuanAPI")
public class imgController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private RecordService recordService;

    @PostMapping("/domain")
    public ResponseEntity<Res> domain(@RequestParam String tag, @RequestParam String type, @RequestBody MultipartFile file){
        long start = System.currentTimeMillis();
        if(!tag.equals("attack") && !tag.equals("defense")){
            return new ResponseEntity<>(new Res(1001, "error tag!", null), HttpStatus.OK);
        }
        System.out.println(tag + " " + type);
        if(file == null){
            return new ResponseEntity<>(new Res(1002, "empty file!", null), HttpStatus.OK);
        }
        if(!fileService.isValidIMG(file)){
            return new ResponseEntity<>(new Res(1003, "error file!", null), HttpStatus.OK);
        }
        Record record = domainService.domain(tag, type, file);
        long end = System.currentTimeMillis();
        log.info("a domain request: " + "tag=" + tag + " " + "type=" + type + " " + "times=" + timeUtil.calculateSec(start, end));
        return new ResponseEntity<>(new Res(1000, "success!", record), HttpStatus.OK);
    }

    @PostMapping("/domainByURL")
    public ResponseEntity<Res> domain(@RequestParam String tag, @RequestParam String type, @RequestParam String imgURL){
        String attackedImgPath = fileService.getImgPath(imgURL);
        long start = System.currentTimeMillis();
        if(!tag.equals("attack") && !tag.equals("defense")){
            return new ResponseEntity<>(new Res(1001, "error tag!", null), HttpStatus.OK);
        }
        System.out.println(tag + " " + type);
        if(!new File(attackedImgPath).exists()){
            return new ResponseEntity<>(new Res(1002, "empty file!", null), HttpStatus.OK);
        }
        Record record = domainService.domain(tag, type, imgURL);
        long end = System.currentTimeMillis();
        log.info("a domain request: " + "tag=" + tag + " " + "type=" + type + " " + "times=" + timeUtil.calculateSec(start, end));
        return new ResponseEntity<>(new Res(1000, "success!", record), HttpStatus.OK);
    }

    @RequestMapping("/getRecentRecords")
    public ResponseEntity<List<Record>> getRecentRecords(){
        return new ResponseEntity<>(recordService.getRecentRecord(), HttpStatus.OK);
    }

    @RequestMapping("getRecentResRecords")
    public ResponseEntity<List<ResRecord>> getRecentResRecords(){
        return new ResponseEntity<>(recordService.getRecentResRecord(), HttpStatus.OK);
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

    @RequestMapping("/getLocalPath")
    public ResponseEntity<String> getLocalPath(@RequestParam String url){
        return new ResponseEntity<>(fileService.getImgPath(url), HttpStatus.OK);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Res {
    int code;
    String msg;
    Record record;
}
