package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

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
}
