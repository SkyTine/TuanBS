package com.yaxin.tuanbs.controller;

import com.yaxin.tuanbs.entity.Record;
import com.yaxin.tuanbs.service.DomainService;
import com.yaxin.tuanbs.service.FileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Autowired
    private DomainService domainService;

    @PostMapping("/domain")
    public ResponseEntity<Res> domain(@RequestParam String tag, @RequestParam String type, @RequestBody MultipartFile file){
//        ResRecord resRecord = new ResRecord();
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
        System.out.println("running");
        return new ResponseEntity<>(new Res(1000, "success", domainService.domain(tag, type, file)), HttpStatus.OK);
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
