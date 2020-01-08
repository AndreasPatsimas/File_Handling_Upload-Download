package org.patsimas.file.utils;

import com.jcraft.jsch.*;


import java.text.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;


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

    // deletes only directory, no simple file
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

    @SuppressWarnings("unchecked")
    public static void deleteRemoteFilesWithSamePrefix(ChannelSftp channelSftp, String path, String prefix)
            throws SftpException {

        final Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        files.forEach(file -> {
            if(file.getFilename().startsWith(prefix)) {
                try {
                    if(file.getAttrs().isDir())
                        deleteNonEmptyDirectory(channelSftp, path + file.getFilename());
                    else
                        channelSftp.rm(path + file.getFilename());
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void deleteRemoteFilesExceptLastInserted(ChannelSftp channelSftp, String path, String extension)
            throws SftpException {

        final Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        List<ChannelSftp.LsEntry> specFiles = files.stream()
                .filter(file -> file.getFilename().endsWith(extension))
                .sorted(Comparator.comparing(f -> {
                    if(f.getFilename().endsWith(extension))
                        return f.getAttrs().getMtimeString();
                    else
                        throw new RuntimeException("no expected file: " + extension);
                }))
                .collect(Collectors.toList());

        if(specFiles.size() > 0)
            specFiles.remove(specFiles.size() - 1);

        specFiles.forEach(specFile -> {
            if(specFile.getFilename().endsWith(extension)){

                try {
                    channelSftp.rm((path + specFile.getFilename()));
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void remoteListFilesForFolder(ChannelSftp channelSftp, String path) throws SftpException {

        final Collection<ChannelSftp.LsEntry> files = channelSftp.ls(path);

        files.forEach(file -> {

            if(file.getAttrs().isDir() && !(file.getFilename().equals(".") || file.getFilename().equals(".."))) {
                try {
                    remoteListFilesForFolder(channelSftp, path + file.getFilename() + "/");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                if(!(file.getFilename().equals(".") || file.getFilename().equals("..")))
                    System.out.println(file.getFilename());
        });
        
    }

    public static boolean isFileExpired(ChannelSftp.LsEntry file, Long retentionPolicyInDays) throws ParseException {

        Long diff = DAYS.between(getFileLocalDate(file.getAttrs().getMtimeString()), getTodayLocalDate());

        if(diff > retentionPolicyInDays)
            return true;

        return false;
    }

    private static LocalDate getFileLocalDate(String fileDate) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        Date date = formatter.parse(fileDate);

        Instant instant = date.toInstant();

        LocalDate localDate = instant.atZone(ZoneId.of("Europe/Athens")).toLocalDate();

        return localDate;
    }

    private static LocalDate getTodayLocalDate(){

        return Instant.now().atZone(ZoneId.of("Europe/Athens")).toLocalDate();
    }
}


//                DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" );
//
//                ZonedDateTime zdt = ZonedDateTime.parse( file.getAttrs().getMtimeString() , f );
//                LocalDate localDate = zdt.toLocalDate();