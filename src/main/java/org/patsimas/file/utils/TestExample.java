package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.patsimas.file.utils.MyFileUtils.*;
import static org.patsimas.file.utils.MyRemoteFileUtils.*;

public class TestExample {

    public static void main(String[] args) throws IOException, JSchException, SftpException, ParseException {

//        createDirectoryFile("C:\\Directory1");

        //MyFileUtils.createMultipleDirectories("C:\\Directory2\\Sub2\\Sub-Sub2");

//        Path path1 = Paths.get("C:/Directory1");
//        Path path2 = Paths.get("C:/Directory1/wf.xml");
//        Path path3 = Paths.get("C:/Directory1/2f.xml");
//        Path path4 = Paths.get("C:/Directory1/haskl.xml");
//
//        try {
//            FileTime creationTime1 = (FileTime) Files.getAttribute(path1, "creationTime");
//
//            FileTime creationTime2 = (FileTime) Files.getAttribute(path2, "creationTime");
//
//            FileTime creationTime3 = (FileTime) Files.getAttribute(path3, "creationTime");
//
//            FileTime creationTime4 = (FileTime) Files.getAttribute(path4, "creationTime");
//
//            List<FileTime> fileTimes  = new ArrayList<>(4);
//
//            fileTimes.add(creationTime1);
//            fileTimes.add(creationTime2);
//            fileTimes.add(creationTime3);
//            fileTimes.add(creationTime4);
//
//            FileTime ap = fileTimes.stream().map(fileTime -> fileTime).max(FileTime::compareTo).get();
//
//            System.out.println(ap.compareTo(creationTime4));
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));
        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));

        boolean deletedflag = false;
//
//        MyFileUtils.deleteDirectory(new File("//ICAP-FS-CL3FS1/Archiving/BUSINESS_REGISTRY/GEMHANALYTICS/TEST - Copy"));
//
        Session session = setupJsch("wasprd02", "root", "banana");

        ChannelSftp channelSftp = openSFTPConnection(session);

//        closeSFTPConnection(session);

//        deleteNonEmptyDirectory(channelSftp,"/tmp/af/teersempty");

//        deleteRemoteFilesWithSameExtension(channelSftp, "/tmp/af/", ".xlsx");

//        deleteRemoteFilesWithSamePrefix(channelSftp, "/tmp/af/", "job_");

//        deleteRemoteFilesExceptLastInserted(channelSftp, "/tmp/af/", ".txt");

        //remoteListFilesForFolder(channelSftp, "/tmp/af/");

//        channelSftp.rm("/tmp/af/ap.txt");
//        channelSftp.rmdir("/tmp/af"); // This method removes the file from remote server
//        deletedflag = true;
//        if(deletedflag){
//            System.out.println("File deleted successfully.");
//        }

        //exceedRetentionPolicy(channelSftp, "/tmp/.com_ibm_tools_attach");

        closeSFTPConnection(session);

//        deleteFilesWithSamePrefix("C:\\Directory1", "dailyReport_08.*\\.txt");

//        deleteFilesWithSameExtension("C:\\Directory1", ".xlsx");

    }

}
