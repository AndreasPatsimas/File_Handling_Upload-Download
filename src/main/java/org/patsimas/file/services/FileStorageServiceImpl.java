package org.patsimas.file.services;

import lombok.extern.slf4j.Slf4j;
import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.exceptions.FileStorageException;
import org.patsimas.file.exceptions.MyFileNotFoundException;
import org.patsimas.file.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
