package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.text.ParseException;

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
    }

}
