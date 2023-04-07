package com.ftp.client.repositories;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/** Класс отвечающий за связь с FTPServer'ом */

@Component
public class FtpClient {
    @Value("${server}")
    private String server;
    @Value("${port}")
    private int port;
    @Value("${ftp-client-user}")
    private String user;
    @Value("${password}")
    private String password;
    private FTPClient ftp;

    public FtpClient() {
    }

    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }


    public void open() throws IOException {

        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);

    }

    public void close() throws IOException {
        ftp.disconnect();
    }

    public Collection<String> listFiles(String path) throws IOException {
        FTPFile[] files = ftp.mlistDir(path);

        return Arrays.stream(files)
                .map(e->{
                    byte[] bytes = StringUtils.getBytesIso8859_1(e.getName());
                    return StringUtils.newStringUtf8(bytes);
                })
                .collect(Collectors.toList());
    }
    public Collection<String> testListFiles(String path) throws IOException {
        FTPFile[] files = ftp.listFiles(path);

        return Arrays.stream(files)
                .map(e->{
                    byte[] bytes = StringUtils.getBytesIso8859_1(e.getName());
                    return StringUtils.newStringUtf8(bytes);
                })
                .collect(Collectors.toList());
    }
    public FTPFile[] getFilesFromDir(String path) throws IOException {
        return ftp.mlistDir(path);
    }


}
