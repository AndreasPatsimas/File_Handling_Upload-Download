package org.patsimas.file.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.patsimas.file.clients.AwsClient;
import org.patsimas.file.domain.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
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
    public void loadFileAsResource(String directory, String fileName) throws IOException {



        S3Object s3object = awsClient.s3Client().getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        File file = new File(buildPath(directory, fileName));
        file.createNewFile();
        FileUtils.copyInputStreamToFile(inputStream, file);

        //return null;
    }

    private String buildPath(String directory, String fileName){
        StringBuilder sb = new StringBuilder();
        sb.append(directory)
                .append("/")
                .append(fileName);
        return sb.toString();
    }
}
