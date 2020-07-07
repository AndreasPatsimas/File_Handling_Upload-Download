package org.patsimas.file.services;

import org.patsimas.file.domain.UploadFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface FileStorageService {

    UploadFileResponse uploadFile(MultipartFile file);

    List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files);

    Resource loadFileAsResource(String fileName);

    Resource export();

    void exportFbz(HttpServletResponse response);
}
