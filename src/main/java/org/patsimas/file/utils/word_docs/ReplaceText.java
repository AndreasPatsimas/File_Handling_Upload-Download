package org.patsimas.file.utils.word_docs;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class ReplaceText {

    public static void main(String [] args) throws Exception {

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("C:/data/Επιστολή ΕΙΣΠΡΑΚΤΕΟΥ ποσού.docx"));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("customerName")) {
                        text = text.replace("customerName", "ΠΑΕ ΑΡΗΣ");
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("date")) {
                        text = text.replace("date", "19/11/2021");
                        r.setText(text, 0);
                    }
                }
            }
        }
//        for (XWPFTable tbl : doc.getTables()) {
//            for (XWPFTableRow row : tbl.getRows()) {
//                for (XWPFTableCell cell : row.getTableCells()) {
//                    for (XWPFParagraph p : cell.getParagraphs()) {
//                        for (XWPFRun r : p.getRuns()) {
//                            String text = r.getText(0);
//                            if (text != null && text.contains("needle")) {
//                                text = text.replace("needle", "haystack");
//                                r.setText(text,0);
//                            }
//                        }
//                    }
//                }
//            }
//        }
        doc.write(new FileOutputStream("C:/data/output1.docx"));
    }
}
