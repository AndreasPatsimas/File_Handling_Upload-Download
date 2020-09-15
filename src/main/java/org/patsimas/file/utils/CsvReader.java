package org.patsimas.file.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CsvReader {

    public static void main(String [] args){

        String csvFile = "C:\\Directory1\\Εμπορικό.csv";

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println(Arrays.asList(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
