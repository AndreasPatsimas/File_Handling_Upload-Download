package org.patsimas.file.utils.pdfs;

import com.docmosis.SystemManager;
import com.docmosis.document.DocumentProcessor;
import com.docmosis.template.population.DataProviderBuilder;
import com.docmosis.util.Configuration;

import java.io.File;

public class SimpleRender {

    public static void main(String [] args){

        String key = "0MAD-KMWK-JALP-DKIA-EBBH-1ELH-PBKQ-MS91-23ED-8-C01C";
        String site = "Free Trial License";

        // Create the initialisation configuration
        Configuration config = new Configuration();
        config.setKeyAndSite(key, site);

        // Tell Docmosis to use one remote converter on port 2100 of this local
        // machine.
       // config.setConverterPoolConfiguration("localhost:2100");

        // Use the DataProviderBuilder to build the data provider from Strings.
        DataProviderBuilder dpb = new DataProviderBuilder();

        dpb.add("date", "12 Jul 2016");
        dpb.add("message", "This Docmosis Document Engine is working!");

        try {
            // Initialise the system based on configuration
            SystemManager.initialise(config);

            File templateFile = new File("C:/Directory1/docmosis/WelcomeTemplate.doc");
            File outputFile = new File("C:/Directory1/docmosis/newDocument.pdf");

            if (!templateFile.canRead()) {
                System.err.println("\nCannot find '" + templateFile + "' in: " + new File("").getCanonicalPath());
            } else {

                if (DocumentProcessor.hasOnlineConverters()) {

                    // Create the document
                    DocumentProcessor.renderDoc(templateFile, outputFile, dpb.getDataProvider());

                    System.out.println("\nCreated: " + outputFile.getCanonicalPath());

                } else {
                    System.err.println(
                            "\nRemote Converter not running.  Have you started the Converter using the runConverter script?");
                }

            }
        } catch (Exception e) {
            System.err.println("\nPlease check the following: " + e.getMessage());
        } finally {

            // Shutdown the system
            SystemManager.release();
        }
    }
}
