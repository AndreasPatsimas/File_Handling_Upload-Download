package org.patsimas.file.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.exceptions.FileStorageException;
import org.patsimas.file.exceptions.MyFileNotFoundException;
import org.patsimas.file.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {

        log.info("Upload file {} process begins", file.getOriginalFilename());

        String fileName = storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        log.info("Upload file process completed");

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @Override
    public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files) {

        log.info("Upload {} files process begins", files.length);

        List<UploadFileResponse> uploadFileResponse = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        log.info("Upload {} files process completed", files.length);

        return uploadFileResponse;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {

        log.info("Load file {} as Resource process begins.", fileName);

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {

                log.info("Load file as Resource process completed.");

                return resource;
            }

            else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        }

        catch (MalformedURLException ex) {

            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public Resource export() {

        log.info("Export accounts as Resource process begins.");

        try {

            createXlsxFile();

            File file = new File("accounts.xlsx");

            Path path = file.toPath().toAbsolutePath().normalize();

            Resource resource = new UrlResource(path.toUri());

            if(resource.exists()) {

                log.info("Export accounts as Resource process completed.");

                return resource;
            }

            else {
                throw new MyFileNotFoundException("File not found accounts.xlsx");
            }
        }

        catch (MalformedURLException ex) {

            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void exportFbz(HttpServletResponse response) {

        log.info("Export accounts as Resource process begins.");

        createXlsxFile();

        File file = new File("accounts.xlsx");

        try {
            setFileToResponse(file, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.delete();
    }

    @Override
    public void downloadZipFile(HttpServletResponse response) {

        log.info("Download zip file process begins.");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        List<String> fileNames = Stream.of("C:/data/output.docx", "C:/data/output1.docx").collect(Collectors.toList());

        System.out.println("############# file size ###########" + fileNames.size());

        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                FileSystemResource resource = new FileSystemResource(file);

                ZipEntry e = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
                // Configure the zip entry, the properties of the file
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                // etc.
                zippedOut.putNextEntry(e);
                // And the content of the resource:
                StreamUtils.copy(resource.getInputStream(), zippedOut);
                zippedOut.closeEntry();
            }
            zippedOut.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Download zip file process end.");
    }

    private void setFileToResponse(File file, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"");
        response.getOutputStream().write(FileUtils.readFileToByteArray(file));
        response.flushBuffer();
    }

    private void createXlsxFile(){
        try (OutputStream os =new FileOutputStream("accounts.xlsx")) {
            Workbook wb = new Workbook(os, "MyApplication", "1.0");
            Worksheet ws = wb.newWorksheet("Sheet 1");
            ws.value(0, 0, "This is a string in A2");
            ws.value(0, 1, new Date());
            ws.value(0, 2, 1234);
            ws.value(0, 3, 123456L);
            ws.value(0, 4, 1.234);
            wb.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

}
