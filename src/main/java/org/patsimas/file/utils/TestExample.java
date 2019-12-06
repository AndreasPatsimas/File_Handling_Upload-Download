package org.patsimas.file.utils;

import java.io.File;

public class TestExample {

    public static void main(String[] args) {

        FileUtils.createDirectoryFile("C:\\Directory1");

        //FileUtils.createMultipleDirectories("C:\\Directory2\\Sub2\\Sub-Sub2");

        FileUtils.deleteFile("C:\\Directory1");

    }
}
