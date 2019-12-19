package org.patsimas.file.utils;

import java.io.File;
import java.io.IOException;

public class MyFileUtils {

    public static void createDirectoryFile(String pathname){

        File file = new File(pathname);

        if (!file.exists()) {

            if (file.mkdir()) {

                System.out.println("Directory is created!");
            } else {

                System.out.println("Failed to create directory!");
            }
        }
    }

    public static void createMultipleDirectories(String pathname){

        File files = new File(pathname);

        if (!files.exists()) {

            if (files.mkdirs()) {

                System.out.println("Multiple directories are created!");
            } else {

                System.out.println("Failed to create multiple directories!");
            }
        }
    }

    public static void deleteDirectory(File file)
            throws IOException {

        if (file.isDirectory()) {

            if (file.list().length == 0) {

                deleteFile(file);

            } else {

                String files[] = file.list();

                for (String temp : files) {

                    deleteDirectory(new File(file, temp));
                }

                if (file.list().length == 0) {
                    deleteFile(file);
                }
            }

        } else {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file){

        try{

            if(file.delete()){

                System.out.println(file.getName() + " is deleted!");
            }else{

                System.out.println("Delete operation is failed.");
            }

        }
        catch(Exception e){

            e.printStackTrace();
        }
    }

    public static void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

}
