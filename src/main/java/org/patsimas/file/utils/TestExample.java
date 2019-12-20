package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.IOException;

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

        //closeSFTPConnection(session);

        //deleteNonEmptyDirectory(channelSftp,"/tmp/af");

//        channelSftp.rm("/tmp/af/ap.txt");
//        channelSftp.rmdir("/tmp/af"); // This method removes the file from remote server
//        deletedflag = true;
//        if(deletedflag){
//            System.out.println("File deleted successfully.");
//        }

        closeSFTPConnection(session);

        //https://www.google.com/search?sxsrf=ACYBGNS3JES54-6vWUFypB20JAE5sYJX-A%3A1576787481017&source=hp&ei=GN77Xcb4O6-HwPAPrOee8A8&q=delete+files+with+prefix+java&oq=delete+files+by+prefix&gs_l=psy-ab.3.1.0i22i30l3.1465.1768724..1771905...7.0..0.229.3758.0j23j1....2..0....1..gws-wiz.....10..35i39j0i131j0i322j0j35i362i39j35i322i362i39j0i203j0i333.PE8bXFElVGk
        //https://stackoverflow.com/questions/7240519/delete-files-with-same-prefix-string-using-java

    }

}
