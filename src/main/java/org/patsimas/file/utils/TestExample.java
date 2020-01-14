package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.patsimas.file.utils.MyRemoteFileUtils.*;

public class TestExample {

    public static void main(String[] args) throws IOException, JSchException, SftpException, ParseException {

//        Session session = setupJsch("****", "****", "****");
//
//        ChannelSftp channelSftp = openSFTPConnection(session);
//
//        deleteRemoteFilesWithSamePrefixOrSuffix(channelSftp, "/tmp/af/", "dailyReport_08.*\\.txt");
//
//        closeSFTPConnection(session);

//        List<File> files = Files.list(new File("C:/Directory1/ge").toPath()).map(Path::toFile)
//                .collect(Collectors.toList());

        //files.forEach(file -> System.out.println(file.getAbsolutePath()));

        File fileDir = new File("C:/Directory1");
        File fileGe = new File("C:/Directory1/ge");


        List<File> filesGe = Arrays.asList(fileGe.listFiles());

        List<String> filesDir = Arrays.asList(fileDir.list());

        filesGe.forEach(file -> {

            if(!filesDir.contains(file.getName()))
                System.out.println(file.getAbsolutePath());
        });


        //boolean b = files.contains(new File("C:/Directory1/ap.txt"));

        //System.out.println(b);

    }

}
