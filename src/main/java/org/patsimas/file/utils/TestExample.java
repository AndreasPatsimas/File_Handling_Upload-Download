package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import static org.patsimas.file.utils.MyFileUtils.*;
import static org.patsimas.file.utils.MyRemoteFileUtils.*;

public class TestExample {

    public static void main(String[] args) throws IOException, JSchException, SftpException {

//        createDirectoryFile("C:\\Directory1");

        //MyFileUtils.createMultipleDirectories("C:\\Directory2\\Sub2\\Sub-Sub2");

//        Path path = Paths.get("C:/mysql-init.txt");
//
//        try {
//            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
//            System.out.println(creationTime);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));
        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));

//        boolean deletedflag = false;
//
//        MyFileUtils.deleteDirectory(new File("//ICAP-FS-CL3FS1/Archiving/BUSINESS_REGISTRY/GEMHANALYTICS/TEST - Copy"));
//
//        Session session = setupJsch("wasprd02", "root", "banana");
//
//        ChannelSftp channelSftp = openSFTPConnection(session);
//
//        closeSFTPConnection(session);
//
//        deleteNonEmptyDirectory(channelSftp,"/tmp/af");
//
//        channelSftp.rm("/tmp/af/ap.txt");
//        channelSftp.rmdir("/tmp/af"); // This method removes the file from remote server
//        deletedflag = true;
//        if(deletedflag){
//            System.out.println("File deleted successfully.");
//        }
//
//        closeSFTPConnection(session);

//        deleteFilesWithSamePrefix("C:\\Directory1", "dailyReport_08.*\\.txt");

//        deleteFilesWithSameExtension("C:\\Directory1", ".xlsx");

    }

}
