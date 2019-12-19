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

        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));
        //listFilesForFolder(new File("//ICAP-FS-CL3FS1/Archiving/2019/BSClipping"));

        boolean deletedflag = false;

        //MyFileUtils.deleteDirectory(new File("//ICAP-FS-CL3FS1/Archiving/BUSINESS_REGISTRY/GEMHANALYTICS/TEST - Copy"));

        Session session = setupJsch("****", "****", "****");

        ChannelSftp channelSftp = openSFTPConnection(session);

        closeSFTPConnection(session);

//        channelSftp.rm("/tmp/ap.html"); // This method removes the file from remote server
//        deletedflag = true;
//        if(deletedflag){
//            System.out.println("File deleted successfully.");
//        }


        System.out.println(channelSftp.isConnected());

//        String server = "wasprd02";
//        int port = 21;
//        String user = "root";
//        String pass = "banana";
//        FTPClient ftpClient = new FTPClient();
//
//        try {
//
//            ftpClient.connect(server);
//
//            int replyCode = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                System.out.println("Connect failed");
//                return;
//            }
//
//            boolean success = ftpClient.login(user, pass);
//
//            if (!success) {
//                System.out.println("Could not login to the server");
//                return;
//            }
//            else
//                System.out.println(success + " Success!!!!");
//            String fileToDelete = "/repository/video/cool.mp4";
//
//            boolean deleted = ftpClient.deleteFile(fileToDelete);
//            if (deleted) {
//                System.out.println("The file was deleted successfully.");
//            } else {
//                System.out.println("Could not delete the  file, it may not exist.");
//            }
//
//        } catch (IOException ex) {
//            System.out.println("Oh no, there was an error: " + ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            // logs out and disconnects from server
//            try {
//                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }

    }

}
