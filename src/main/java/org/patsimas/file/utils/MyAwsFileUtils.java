package org.patsimas.file.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MyAwsFileUtils {


    public static void createFolder(String folderName){


        AWSCredentials credentials = new BasicAWSCredentials("****",
                "****");
        AmazonS3 s3 =
                new AmazonS3Client(credentials, new ClientConfiguration().withProtocol(Protocol.HTTP));

        InputStream input = new ByteArrayInputStream(new byte[0]);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        s3.putObject(new PutObjectRequest("ariss3", folderName + "/", input, metadata));
    }
}
