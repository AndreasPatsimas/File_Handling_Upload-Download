package org.patsimas.file.utils;

import org.patsimas.file.exceptions.FileStorageException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

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

    // prefix must be sth like this: dailyReport_08.*\\.txt
    public static void deleteFilesWithSamePrefix(String directoryPath, String prefix){

        final File folder = new File(directoryPath);

        final File[] files = folder.listFiles( new FilenameFilter() {
            @Override
            public boolean accept( final File dir,
                                   final String name ) {
                return name.matches( prefix );
            }
        } );
        for ( final File file : files ) {
            if ( !file.delete() ) {
                System.err.println( "Can't remove " + file.getAbsolutePath() );
            }
        }
    }

    public static void deleteFilesWithSameExtension(String directoryPath, String extension){

        File folder = new File(directoryPath);

        File fList[] = folder.listFiles();

        for (int i = 0; i < fList.length; i++) {

            if (fList[i].getName().endsWith(extension)) {

                fList[i].delete();
            }
        }
    }

    public static void emptyDirectory(File file){
        if (file.isDirectory()) {

            if (file.list().length != 0) {

                for (String temp : file.list()) {

                    deleteFile(new File(file.getAbsolutePath() + "/" + temp));
                }
            }
        }
    }

    public static String storeFileFromServer(MultipartFile file, String path) {

        Path fileStorageLocation = Paths.get(path)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public static void listFilesForFolder(File folder) throws IOException {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
//                Path file = fileEntry.toPath();
//                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//                System.out.println("creationTime: " + attr.creationTime());
//                System.out.println("lastAccessTime: " + attr.lastAccessTime());
//                System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
            }
        }
    }

    public static void openFile(String path){
        try {

            File file = new File(path);
            if (file.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }

            } else {
                System.out.println("File is not exists!");
            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
