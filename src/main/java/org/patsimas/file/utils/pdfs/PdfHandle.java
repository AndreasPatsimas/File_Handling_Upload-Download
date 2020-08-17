package org.patsimas.file.utils.pdfs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfHandle {

    //https://www.baeldung.com/java-pdf-creation

    public static void main(String [] args){

        createPdf();
    }

    private static void createPdf(){

        try{

//            Path path = Paths.get(ClassLoader.getSystemResource("images" + File.separator + "sol.png").toURI());

            Document document = new Document();
            PdfWriter writer = PdfWriter
                    .getInstance(document, new FileOutputStream("C:/Directory1/sol/iTextHelloWorld.pdf"));

            document.open();

//            Image img = Image.getInstance(path.toAbsolutePath().toString());
//            document.add(img);

            onStartPage(writer, document);

//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//
//            Chunk chunk = new Chunk("Hello World", font);
//
//            document.add(chunk);

            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void onStartPage(PdfWriter writer, Document document) {

        createImage(writer);
        createTable(writer, document);

//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 30, 800, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(" pour réussir votre prochain concours."), 400, 800, 0);
    }

    private static void createImage(PdfWriter writer){

        try {
            Path path = Paths.get(ClassLoader.getSystemResource("images" + File.separator + "sol.png").toURI());
            Image image = Image.getInstance(path.toAbsolutePath().toString());
            //image.
            image.setAlignment(Element.ALIGN_RIGHT);
            image.setAbsolutePosition(1, 730);
            image.scalePercent(90.5f, 90.5f);
            writer.getDirectContent().addImage(image, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(PdfWriter writer, Document document){

        try{

            PdfPTable table = new PdfPTable(2); // 3 columns.
            table.setWidthPercentage(30); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
            cell2.setBorderColor(BaseColor.GREEN);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
//            cell1.setUseBorderPadding(true);
//            cell2.setUseBorderPadding(true);

            table.addCell(cell1);
            table.addCell(cell2);

            document.add(table);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
