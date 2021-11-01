package org.patsimas.file.utils.pdfs;

import java.io.File;

import com.docmosis.SystemManager;
import com.docmosis.document.DocumentProcessor;
import com.docmosis.template.population.DataProviderBuilder;

/**
 * A simple example showing Docmosis creating a PDF with dynamic data from a DOC
 * template.
 */
public class DocmosisConfigFiles {

	public static void main(String[] args) {

		// Use the DataProviderBuilder to build the data provider from Strings
		DataProviderBuilder dpb = new DataProviderBuilder();

		dpb.add("general-info", "Η εταιρεία έχει το νομικό τύπο της Ανώνυμης Εταιρείας και δραστηριοποιείται κυρίως στην Ελλάδα, στην Ευρώπη (Κύπρο) και στην Τουρκία. Η εταιρεία εισάγει και εμπορεύεται πλαστικά και χημικά (πρώτες ύλες) προοριζόμενες για βιομηχανικές χρήσεις και αντιπροσωπεύει εργοστάσια της ημεδαπής ή αλλοδαπής, παραγωγής των ανωτέρω πρώτων υλών.\n" +
				"Οι εγκαταστάσεις βρίσκονται σε ιδιόκτητο ακίνητο στη Λεωφόρο Βουλιαγμένης Νο 40.\n" +
				"Η εταιρεία δεν ανήκει σε όμιλο, ούτε είναι εισηγμένη σε χρηματιστήριο και η κάθε χρήση ξεκινάει 1η Ιανουαρίου κάθε έτους και λήγει την 31η Δεκεμβρίου του ίδιου έτους. Είναι κερδοφόρος με ισχυρή καθαρή θέση και το ύψος του δανεισμού της αντιπροσωπεύει το 75% περίπου του συνόλου των υποχρεώσεων. Σύμφωνα με τις εκτιμήσεις της διοίκησης δεν εντοπίζονται παράγοντες (γεγονότα ή συνθήκες) που θέτουν σε κίνδυνο τη συνέχιση της δραστηριότητας.\n");
		//dpb.add("message", "This Docmosis Document Engine is working!");

		try {
			// Initialize the system using the "docmosis.properties" file
			SystemManager.initialise();

			File templateFile = new File("C:/Directory1/docmosis/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της.docx");
			File outputFile = new File("C:/Directory1/docmosis/Φ.Ε. 205.1 - Κατανόηση της Οντότητας και του Περιβάλλοντός της.pdf");

			if (!templateFile.canRead()) {
				System.err.println("\nCannot find '" + templateFile + "' in: " + new File("").getCanonicalPath());
			} else {

				// Create the document
				DocumentProcessor.renderDoc(templateFile, outputFile, dpb.getDataProvider());

				System.out.println("\nCreated: " + outputFile.getCanonicalPath());
			}
		} catch (Exception e) {
			System.err.println("\nPlease check the following: " + e.getMessage());
		} finally {

			// Shutdown the system
			SystemManager.release();
		}
	}
}
