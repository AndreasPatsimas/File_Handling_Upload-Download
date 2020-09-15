package org.patsimas.file.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.patsimas.file.domain.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.patsimas.file.utils.MyRemoteFileUtils.*;

public class TestExample {

    public static void main(String[] args) throws IOException, JSchException, SftpException, ParseException {

//        Session session = setupJsch("****", "****", "****");
//
//        ChannelSftp channelSftp = openSFTPConnection(session);
//
//        Collection<ChannelSftp.LsEntry> excludefiles = channelSftp.ls("/tmp/af");
//
//        List<String> excludeFileNames = excludefiles.stream().map(excludefile -> excludefile.getFilename()).collect(Collectors.toList());
//
//        System.out.println(excludeFileNames);
//
//        //excludefiles.forEach(excludefile -> System.out.println(excludefile.getFilename()));
//
//        closeSFTPConnection(session);

        Map<String, String> mapping = new
                HashMap<String, String>();
        mapping.put("name", "name");
        mapping.put("rollno", "rollNo");
        mapping.put("department", "department");
        mapping.put("result", "result");
        mapping.put("cgpa", "pointer");

        HeaderColumnNameTranslateMappingStrategy<Student> strategy =
                new HeaderColumnNameTranslateMappingStrategy<Student>();

        strategy.setType(Student.class);
        strategy.setColumnMapping(mapping);

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader
                    ("C:\\Directory1\\studentData.csv"));
        }
        catch (FileNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CsvToBean csvToBean = new CsvToBean();

        // call the parse method of CsvToBean
        // pass strategy, csvReader to parse method
        List<Student> list = csvToBean.parse(strategy, csvReader);

        // print details of Bean object
        for (Student e : list) {
            System.out.println(e);
        }

        //MyAwsFileUtils.makeFilePublic("images/bulba.jpg");

        //MyFileUtils.openFile("C:/Directory1/aris.docx");
    }

}
