package org.patsimas.file.utils;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.*;
import java.util.Date;

public class ExportToXlsx {

    public static void main(String [] args){

        export();
    }

    public static void export(){
        try (OutputStream os =new FileOutputStream("accounts.xlsx")) {
            Workbook wb = new Workbook(os, "MyApplication", "1.0");
            Worksheet ws = wb.newWorksheet("Sheet 1");
            ws.value(0, 0, "This is a string in A1");
            ws.value(0, 1, new Date());
            ws.value(0, 2, 1234);
            ws.value(0, 3, 123456L);
            ws.value(0, 4, 1.234);
            wb.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
