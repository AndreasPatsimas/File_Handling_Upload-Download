package org.patsimas.file.utils.pdfs;

import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlLoadOptions;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.TextFragmentCollection;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class PdfHandle {

    private static final String BASE_PATH = "C:/data/";
    private static final String SRC = "C:/data/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της.pdf";
    private static final String DEST = "C:/data/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της - modified.pdf";


    public static void main(String [] args) throws Exception {
        File file = new File(SRC);
        System.out.println(file.getAbsolutePath());
        editPdfText("Γενικές πληροφορίες", "agathi");
    }

    private static void urlToPdf(String url, String token){

        try {

            URL pageUrl = new URL(url);
            URLConnection urlConnection = pageUrl.openConnection();
            //https://stackoverflow.com/questions/21076179/pkix-path-building-failed-and-unable-to-find-valid-certification-path-to-requ
            //keytool -import -keystore "C:\Program Files\Java\jdk1.8.0_211\jre\lib\security\cacerts" -file solae.cer
            if (!ObjectUtils.isEmpty(token))
                urlConnection.addRequestProperty("Authorization", "Bearer " + token);

            Document doc = new Document(urlConnection.getInputStream(), new HtmlLoadOptions(url));
            doc.save(DEST);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void editPdfText(String search,  String replace){

        Document pdfDocument = new Document(SRC);

        // Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(search);

        // Accept the absorber for all the pages
        pdfDocument.getPages().accept(textFragmentAbsorber);

        // Get the extracted text fragments
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();

        // Loop through the fragments
        textFragmentCollection.forEach(textFragment -> {
            textFragment.setText(replace);
        });

        // Save resulting PDF document.
        pdfDocument.save(DEST);
    }

    private static void convertHtmlToPdf(){

        HtmlLoadOptions htmloptions = new HtmlLoadOptions(BASE_PATH);

        // Load HTML file
        Document doc = new Document(BASE_PATH + "d22869f2-e13f-11ea-8b25-0cc47a792c0a_id_d22869f2-e13f-11ea-8b25-0cc47a792c0a.html", htmloptions);

        // Save HTML file
        doc.save(DEST);
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

}
