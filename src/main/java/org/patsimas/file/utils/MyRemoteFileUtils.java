package org.patsimas.file.utils;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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

    @SuppressWarnings("unchecked")
    public static void deleteRemoteFilesWithSameExtension(ChannelSftp channelSftp, String path, String extension)
            throws SftpException {

        Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        files.forEach(file -> {

            if (file.getFilename().endsWith(extension)) {
                try {
                    channelSftp.rm((path + file.getFilename()));
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // prefix must be sth like this: dailyReport_08.*\\.txt
    @SuppressWarnings("unchecked")
    public static void deleteRemoteFilesWithSamePrefix(ChannelSftp channelSftp, String path, String prefix)
            throws SftpException {

        final Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        files.forEach(file -> {
            if(file.getFilename().matches(prefix)) {
                try {
                    channelSftp.rm((path + file.getFilename()));
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void deleteRemoteFilesExceptLastInserted(ChannelSftp channelSftp, String path, String extension)
            throws SftpException, ParseException {

        DateFormat dateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss zzz yyyy");

        final Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        List<ChannelSftp.LsEntry> specFiles = files.stream()
                .filter(file -> file.getFilename().endsWith(extension))
                .collect(Collectors.toList());

        ChannelSftp.LsEntry file = Collections.max(specFiles, Comparator.comparing(f -> {

            if(f.getFilename().endsWith(extension))
                return f.getAttrs().getMtimeString();
            else
                throw new RuntimeException("no expected file: " + extension);
        }));

        System.out.println(file.getFilename());

//        files.forEach(file -> System.out.println(file.getFilename()));
//
//        List list = files.stream().collect(
//                        Collectors.toCollection(
//                                () -> new TreeSet<>((a, b) -> a.getAttrs().getMtimeString().compareTo(b.getAttrs().getMtimeString()))
//                        )
//
//        ).values().stream().map(TreeSet::last).collect(Collectors.toList());




//        List<Date> dates = new ArrayList<>();
//
//        files.stream().map(file ->{
//
//            try {
//                if(file.getFilename().endsWith(extension))
//                    dates.add(dateFormat.parse(file.getAttrs().getMtimeString()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            return dates;
//        }).collect(Collectors.toList());
//
//        Date date = dates.stream().map(date1 -> date1).max(Date::compareTo).orElseThrow(NoSuchElementException::new);
//
//        files.forEach(file -> {
//            if(file.getFilename().endsWith(extension)) {
//                try {
//                    if(!date.toString().equals(file.getAttrs().getMtimeString()))
//                        System.out.println(file.getFilename() + ": " + dateFormat.parse(file.getAttrs().getMtimeString()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
