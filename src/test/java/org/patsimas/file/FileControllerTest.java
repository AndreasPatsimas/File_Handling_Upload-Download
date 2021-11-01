package org.patsimas.file;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=FileControllerTest",
        "spring.jmx.default-domain=FileControllerTest"})
public class FileControllerTest extends BasicWiremockTest {

    private static final String DOWNLOAD_FILE_NAME = "LICENSE.txt";

    //private static final String UPLOAD_FILE_NAME_1 = "ProjectCenter_20191119.xlsx";

    private static final String UPLOAD_FILE_NAME_1 = "Eniaio.pdf";

    @Ignore
    @Test
    public void downloadFile() throws Exception {

        this.mockMvc.perform(
                get(CONTEXT_PATH + "/downloadFile/{fileName:.+}", DOWNLOAD_FILE_NAME).contextPath(CONTEXT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
    }

    @Test
    public void testUploadFile() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", UPLOAD_FILE_NAME_1,
                "multipart/form-data", is);

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                .file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    public void testUploadMultipleFiles() throws Exception {
//
//        //List<MockMultipartFile> mockMultipartFiles = new ArrayList<>(3);
//
//        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", UPLOAD_FILE_NAME_1,
//                "multipart/form-data", is);
//
//        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file", "FINDBIZ-SPECS.pdf",
//                "multipart/form-data", is);
//
//        //mockMultipartFiles.add(mockMultipartFile);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadMultipleFiles")
//                .file(mockMultipartFile1).contentType(MediaType.MULTIPART_FORM_DATA))
//                .andDo(print())
//                .andExpect(status().isOk()).andReturn();
//    }

    @Test
    public void export() throws Exception {

        this.mockMvc.perform(
                get(CONTEXT_PATH + "/export/fbz")
                       // .content(asJsonString(exportListRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .contextPath(CONTEXT_PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
