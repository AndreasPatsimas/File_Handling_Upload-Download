package org.patsimas.file.controllers;

import lombok.extern.slf4j.Slf4j;
import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.services.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class AwsController {

    @Autowired
    AwsService awsService;

    @PostMapping("/aws/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("Upload file {}", file.getName());

        return awsService.uploadFile(file);
    }
}
