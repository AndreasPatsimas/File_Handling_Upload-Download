package org.patsimas.file.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MyAwsFileUtils {


    public static void createFolder(String folderName){


        AmazonS3 s3 = s3Client();

        InputStream input = new ByteArrayInputStream(new byte[0]);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        s3.putObject(new PutObjectRequest("ariss3", folderName + "/", input, metadata));
    }

    public static void makeFilePublic(String fileName){

        AmazonS3 s3 = s3Client();

        s3.setObjectAcl("ariss3", fileName, CannedAccessControlList.PublicRead);

    }

    private static AmazonS3 s3Client(){

        AWSCredentials credentials = new BasicAWSCredentials("****",
                "****");

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }
}
