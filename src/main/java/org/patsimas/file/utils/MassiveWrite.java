package org.patsimas.file.utils;

//https://www.journaldev.com/881/java-append-to-file

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
@Slf4j
public class MassiveWrite {

    public static void main(String[] args){

        massiveWriteColumns(700, "C:/Directory1/help.txt");
    }

    private static void massiveWriteColumns(int lines, String path){

        try{
            File file = new File(path);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            log.info("writing...");
            for (int i = 1; i <= lines; i++){
                br.write("@Column(name = \"a" + i + "\", nullable = false)\n" +
                        "private String a" + i + ";");
                br.newLine();br.newLine();
            }

            br.close();
            fr.close();

            log.info("completed");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
