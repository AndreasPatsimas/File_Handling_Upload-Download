package org.patsimas.file.utils;

import java.io.File;
import java.io.IOException;

public class TestExample {

    public static void main(String[] args) throws IOException {

        //MyFileUtils.createDirectoryFile("C:\\Directory1");

        //MyFileUtils.createMultipleDirectories("C:\\Directory2\\Sub2\\Sub-Sub2");
//
//        MyFileUtils.deleteFile("C:\\Directory1");
//
//        Path path = Paths.get("C:/mysql-init.txt");
//
//        try {
//            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
//            System.out.println(creationTime);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

//        File folder = new File("//ICAP-FS-CL3FS1/Archiving/TEP");
//        listFilesForFolder(folder);

        //listFilesForFolder(new File("C:\\Directory2"));
        //File file = new File("C:\\Directory2");

        MyFileUtils.deleteDirectory(new File("C:/Directory1"));

        //MyFileUtils.deleteDirectory(new File("C:/Directory2"));

    }

}
