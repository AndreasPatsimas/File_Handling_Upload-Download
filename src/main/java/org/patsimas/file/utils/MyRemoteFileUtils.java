package org.patsimas.file.utils;

import com.jcraft.jsch.*;

import java.util.Collection;
import java.util.Properties;


public class MyRemoteFileUtils {

    public static ChannelSftp openSFTPConnection(Session jschSession) throws JSchException {

        ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");

        channelSftp.connect();

        System.out.println("ChannelSftp connection is made: " + channelSftp.isConnected());

        return channelSftp;
    }

    public static void closeSFTPConnection(Session jschSession) {
        if ( jschSession!=null && jschSession.isConnected() ) {

            try {

                jschSession.disconnect();

                System.out.println("ChannelSftp connection is closed: " + !jschSession.isConnected());
            }
            catch (Throwable f) {
                System.err.println("An error occured while trying to disconnect from the FTP server [" + jschSession.getHost() + "].");
            }
        }
    }

    public static Session setupJsch(String remoteHost, String username, String password) throws JSchException {

        JSch jsch = new JSch();

        Session jschSession = jsch.getSession(username, remoteHost);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        jschSession.setConfig(config);
        jschSession.setPassword(password);
        jschSession.connect();

        return jschSession;
    }

    @SuppressWarnings("unchecked")
    public static void deleteNonEmptyDirectory(ChannelSftp channelSftp, String path) throws SftpException {


        Collection<ChannelSftp.LsEntry> fileAndFolderList = channelSftp.ls(path);

        for (ChannelSftp.LsEntry item : fileAndFolderList) {
            if (!item.getAttrs().isDir()) {
                channelSftp.rm(path + "/" + item.getFilename());
            } else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
                try {

                    channelSftp.rmdir(path + "/" + item.getFilename());
                } catch (Exception e) {

                    deleteNonEmptyDirectory(channelSftp, path + "/" + item.getFilename());
                }
            }
        }
        channelSftp.rmdir(path);
    }
}
