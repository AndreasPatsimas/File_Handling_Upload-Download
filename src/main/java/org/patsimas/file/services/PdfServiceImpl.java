package org.patsimas.file.services;

//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {

    @Override
    public void exportToPdf(HttpServletResponse response) {

        log.info("Export aris process begins.");
        createPdfFile();
        File file = new File("aris.pdf");
        try {
            setFileToResponse(file, response);
            log.info("Export aris process end.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            file.delete();
        }
    }

    private void createPdfFile(){

        try{

//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("aris.pdf"));
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//            Chunk chunk = new Chunk("Aris Forever!!!", font);
//
//            document.add(chunk);
//            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFileToResponse(File file, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"");
        response.getOutputStream().write(FileUtils.readFileToByteArray(file));
        response.flushBuffer();
    }
}
