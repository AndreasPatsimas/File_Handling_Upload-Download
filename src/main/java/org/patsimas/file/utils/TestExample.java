package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.patsimas.file.utils.MyFileUtils.listFilesForFolder;
import static org.patsimas.file.utils.MyRemoteFileUtils.*;

public class TestExample {

    public static void main(String[] args) throws IOException, JSchException, SftpException {

        //delete MyFileUtils.createDirectoryFile("C:\\Directory1");

        //MyFileUtils.createMultipleDirectories("C:\\Directory2\\Sub2\\Sub-Sub2");

//        Path path = Paths.get("C:/mysql-init.txt");
//
//        try {
//            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
//            System.out.println(creationTime);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }



        boolean deletedflag = false;


        Session session = setupJsch("****", "****", "****");

        ChannelSftp channelSftp = openSFTPConnection(session);

        closeSFTPConnection(session);

//        channelSftp.rm("/tmp/ap.html"); // This method removes the file from remote server
//        deletedflag = true;
//        if(deletedflag){
//            System.out.println("File deleted successfully.");
//        }


    }

}
