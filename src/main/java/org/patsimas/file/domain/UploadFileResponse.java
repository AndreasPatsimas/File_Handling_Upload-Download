package org.patsimas.file.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadFileResponse {

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;
}
