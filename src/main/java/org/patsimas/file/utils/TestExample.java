package org.patsimas.file.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class TestExample {

    public static void main(String[] args) throws Exception {

        //Instantiating the File class
        String filePath = "C:/data/Επιστολή ΕΙΣΠΡΑΚΤΕΟΥ ποσού.doc";
        File file = new File(filePath);
        //Instantiating the Scanner class to read the file
        Scanner sc = new Scanner(file);
        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine()) {
            String x = sc.nextLine();
            System.out.println(x);
            buffer.append(x);
        }
        String fileContents = buffer.toString();
        System.out.println("Contents of the file: "+ fileContents);
        //closing the Scanner object
        sc.close();
        String oldLine = "<<customerName>>";
        String newLine = "ΠΑΕ ΑΡΗΣ";
        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldLine, newLine);
        //instantiating the FileWriter class
        FileWriter writer = new FileWriter(filePath);
        System.out.println("");
        System.out.println("new data: "+ fileContents);
        writer.append(fileContents);
        writer.flush();
    }
}