package org.patsimas.file.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.patsimas.file.clients.AwsClient;
import org.patsimas.file.domain.UploadFileResponse;
import org.patsimas.file.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource({ "classpath:application.properties" })
@Service
@Slf4j
public class AwsServiceImpl implements AwsService {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${archiving.path}")
    private String archivingPath;

    @Autowired
    AwsClient awsClient;

    @Override
    public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files) {

        log.info("Upload {} files process begins", files.length);

        List<UploadFileResponse> uploadFileResponse = Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        log.info("Upload {} files process completed", files.length);

        return uploadFileResponse;
    }

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) throws IOException {

        log.info("Upload file to AWS server {} process begins", file.getOriginalFilename());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/aws/downloadFile/")
                .path(file.getOriginalFilename())
                .toUriString();

        awsClient.s3Client().putObject(new PutObjectRequest(
                bucketName, file.getOriginalFilename(), file.getInputStream(), new ObjectMetadata()));

        log.info("Upload file to AWS server process completed");

        return UploadFileResponse.builder()
                .fileDownloadUri(fileDownloadUri)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @Override
    public Resource loadFileAsResource(String fileName) {

        log.info("Load file {} as Resource process begins.", fileName);

        try {

            if(downloadFile(fileName)){

                return createResource(fileName);
            }
            else
                throw new MyFileNotFoundException("File not found " + fileName);

        }

        catch (MalformedURLException ex) {

            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    private boolean downloadFile(String fileName) {

        try {

            S3Object s3object = awsClient.s3Client().getObject(bucketName, fileName);

            S3ObjectInputStream inputStream = s3object.getObjectContent();

            File file = new File(archivingPath + fileName);

            file.createNewFile();

            FileUtils.copyInputStreamToFile(inputStream, file);

            return true;
        }
        catch (Exception e){

            e.printStackTrace();

            return false;
        }
    }

    private Resource createResource(String fileName) throws MalformedURLException {

        Path filePath = Paths.get(archivingPath)
                .toAbsolutePath().normalize().resolve(fileName).normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if(resource.exists()) {

            log.info("Load file as Resource process completed.");

            return resource;
        }

        else {
            throw new MyFileNotFoundException("File not found " + fileName);
        }
    }
}
