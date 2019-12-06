package org.patsimas.file.utils;

import java.io.File;

public class FileUtils {

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

    public static void deleteFile(String pathname){

        try{

            File file = new File(pathname);

            if(file.delete()){

                System.out.println(file.getName() + " is deleted!");
            }else{

                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            e.printStackTrace();
        }
    }

}
