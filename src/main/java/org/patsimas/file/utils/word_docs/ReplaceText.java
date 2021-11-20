package org.patsimas.file.utils.word_docs;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class ReplaceText {

    public static void main(String [] args) throws Exception {

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("C:/data/Επιστολή ΕΙΣΠΡΑΚΤΕΟΥ ποσού.docx"));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("cn")) {
                        text = text.replace("cn", "ΠΑΕ ΑΡΗΣ");
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("today")) {
                        text = text.replace("today", LocalDate.now().toString());
                        r.setText(text, 0);
                    }

                    if (text != null && text.contains("basicInfoEndDate")) {
                        text = text.replace("basicInfoEndDate", LocalDate.now().toString());
                        r.setText(text, 0);
                    }

                    if (text != null && text.contains("signer")) {
                        text = text.replace("signer", "Patsiman");
                        r.setText(text, 0);
                    }

                    if (text != null && text.contains("email")) {
                        text = text.replace("email", "apatsimas@solcrowe.gr");
                        r.setText(text, 0);
                    }

                    if (text != null && text.contains("orderCustomerName")) {
                        text = text.replace("orderCustomerName", "NAOS");
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains("balanceAmount")) {
                                text = text.replace("balanceAmount", "2000");
                                r.setText(text, 0);
                            }

                            if (text != null && text.contains("balanceSecurities")) {
                                text = text.replace("balanceSecurities", "1000");
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
        doc.write(new FileOutputStream("C:/data/output.docx"));
    }
}
