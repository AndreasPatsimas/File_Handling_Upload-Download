package org.patsimas.file.utils;

//https://www.journaldev.com/881/java-append-to-file

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
@Slf4j
public class MassiveWrite {

    public static void main(String[] args){

        //massiveWriteColumns(700, "C:/Directory1/entities.txt");
        //massiveWriteConverters(700, "C:/Directory1/converters.txt");
        //massiveWriteDtos(700, "C:/Directory1/dtos.txt");
        massiveWriteTestBuilders(700, "C:/Directory1/test_builders.txt");
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

    private static void massiveWriteConverters(int lines, String path){

        try{
            File file = new File(path);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            log.info("writing...");
            for (int i = 1; i <= lines; i++){
                br.write(".a" + i + "(entities.getA" + i + "())");
                br.newLine();
            }

            br.close();
            fr.close();

            log.info("completed");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void massiveWriteDtos(int lines, String path){

        try{
            File file = new File(path);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            log.info("writing...");
            for (int i = 1; i <= lines; i++){
                br.write("private String a" + i + ";");
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

    private static void massiveWriteTestBuilders(int lines, String path){
        try{
            File file = new File(path);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            log.info("writing...");
            for (int i = 1; i <= lines; i++){
                br.write(".a" + i + "(\"DUMMY\")");
                br.newLine();
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
