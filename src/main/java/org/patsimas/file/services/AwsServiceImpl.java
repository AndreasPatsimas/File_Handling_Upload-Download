package org.patsimas.file.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.file.clients.AwsClient;
import org.patsimas.file.domain.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@PropertySource({ "classpath:application.properties" })
@Service
@Slf4j
public class AwsServiceImpl implements AwsService {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    AwsClient awsClient;

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) throws IOException {

        log.info("Upload file to AWS server {} process begins", file.getOriginalFilename());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
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
}
