package org.patsimas.file.controllers;


import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.services.FileStorageService;
import org.patsimas.file.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/file-handle")
@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

        log.info("Upload file {}", file.getName());

        return fileStorageService.uploadFile(file);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        log.info("Upload {} files", files.length);

        return fileStorageService.uploadMultipleFiles(files);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        log.info("Load file {} as Resource", fileName);

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(request, resource)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/download-zip")
    public ResponseEntity<Resource> downloadZipFile(HttpServletResponse response) {

        log.info("Download zip file process end.");

        fileStorageService.downloadZipFile(response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> export(HttpServletRequest request) {

        log.info("Load file accounts.xlsx as Resource");

        Resource resource = fileStorageService.export();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(request, resource)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/export/fbz")
    public void export(HttpServletResponse response) {

        log.info("Load file accounts.xlsx as Resource");

        fileStorageService.exportFbz(response);

    }

    @GetMapping("/export-pdf")
    public ResponseEntity exportPdf(HttpServletResponse response) {

        log.info("Export aris");

        pdfService.exportToPdf(response);

        return new ResponseEntity<>(HttpStatus.OK);
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
