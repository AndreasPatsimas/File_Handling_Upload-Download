package org.patsimas.file.services;

import org.patsimas.file.domain.UploadFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AwsService {

    UploadFileResponse uploadFile(MultipartFile file) throws IOException;

    Resource loadFileAsResource(String directory, String fileName, String format);
}
