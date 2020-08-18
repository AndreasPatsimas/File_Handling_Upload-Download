package org.patsimas.file.utils.pdfs;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.docraptor.ApiClient;
import com.docraptor.Doc;
import com.docraptor.DocApi;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

@Slf4j
public class PdfHandle {

    //https://www.baeldung.com/java-pdf-creation

    public static final String SRC = "C:/Directory1/sol/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της.pdf";
    public static final String DEST = "C:/Directory1/sol/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της - modified.pdf";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO = Charset.forName("ISO-8859-1");

    public static void main(String [] args) throws Exception {

        //createPdf();

//        PDDocument document = null;
//        document = PDDocument.load(new File(SRC));
//        searchReplace("2016", "1914", true, document);
//        document.save(DEST);
//        document.close();

        DocApi docraptor = new DocApi();
        ApiClient client = docraptor.getApiClient();
        client.setUsername("YOUR_API_KEY_HERE");
        //client.setDebugging(true);

        Doc doc = new Doc();
        doc.setTest(true);                                                   // test documents are free but watermarked
        doc.setDocumentContent("<html><body>Hello World</body></html>");     // supply content directly
        // doc.setDocumentUrl("http://docraptor.com/examples/invoice.html"); // or use a url
        doc.setDocumentType(Doc.DocumentTypeEnum.PDF);                       // PDF or XLS or XLSX
        doc.setName(DEST);                                   // help you find a document later
        doc.setJavascript(true);                                             // enable JavaScript processing
        // prince_options = new PrinceOptions();
        // doc.setPrinceOptions(prince_options);
        // prince_options.setMedia("screen");                                // use screen styles instead of print styles
        // prince_options.setBaseurl("http://hello.com")                     // pretend URL when using document_content
        docraptor.createDoc(doc);

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
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Font font = FontFactory.getFont(BaseFont.HELVETICA, "UTF-8", BaseFont.EMBEDDED, 7.5f, Font.NORMAL, new BaseColor(0, 45, 95));
            //font.setColor();

            Chunk chunk = new Chunk("AΡHΣ 2", font);
            PdfPCell cell2 = new PdfPCell(new Paragraph(chunk));
            cell2.setBorderWidthBottom(3f);
            cell2.setBorderColor(BaseColor.WHITE);
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

    private static void readPdfText(){
        try {

            PDDocument document = PDDocument.load(new File(SRC));
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println(text);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void convertHtmlToPdf(String pdfPath, String htmlPath) throws Exception{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(pdfPath));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(htmlPath), new FileInputStream("CSS"));
        document.close();
    }


    private static void searchReplace (String search, String replace, boolean replaceAll, PDDocument doc) throws Exception {
        PDPageTree pages = doc.getDocumentCatalog().getPages();
        for (PDPage page : pages) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List tokens = parser.getTokens();
            for (int j = 0; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof Operator) {
                    Operator op = (Operator) next;
                    // Tj and TJ are the two operators that display strings in a PDF
                    // Tj takes one operator and that is the string to display so lets update that operator
                    if (op.getName().equals("Tj")) {

                        COSString previous = (COSString) tokens.get(j-1);
                        String string = previous.getString();
                        System.out.println(string);
                        if (replaceAll)
                            string = string.replaceAll(search, replace);
                        else
                            string = string.replaceFirst(search, replace);
                        System.out.println(string);
                        previous.setValue(string.getBytes(UTF_8));
                    } else if (op.getName().equals("TJ")) {

                        COSArray previous = (COSArray) tokens.get(j-1);
                        for (int k = 0; k < previous.size(); k++) {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString) {
                                COSString cosString = (COSString) arrElement;
                                String string = cosString.getString();
                                if (replaceAll)
                                    string = string.replaceAll(search, replace);
                                else
                                    string = string.replaceFirst(search, replace);
                                cosString.setValue(string.getBytes());
                            }
                        }
                    }
                }
            }
            // now that the tokens are updated we will replace the page content stream.
            PDStream updatedStream = new PDStream(doc);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            out.close();
            page.setContents(updatedStream);
        }
    }
}
