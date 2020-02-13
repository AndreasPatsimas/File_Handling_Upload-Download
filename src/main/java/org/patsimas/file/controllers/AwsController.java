package org.patsimas.file.controllers;

import lombok.extern.slf4j.Slf4j;
import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.services.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class AwsController {

    @Autowired
    AwsService awsService;

    @PostMapping("/aws/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("Upload file {}", file.getOriginalFilename());

        return awsService.uploadFile(file);
    }

    @PostMapping("/aws/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        log.info("Upload {} files", files.length);

        return awsService.uploadMultipleFiles(files);
    }

    @GetMapping("/aws/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 HttpServletRequest request) throws IOException {

        log.info("Load file {} as Resource", fileName);

        Resource resource = awsService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(request, resource)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String contentType(HttpServletRequest request, Resource resource){

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return contentType;
    }
}
