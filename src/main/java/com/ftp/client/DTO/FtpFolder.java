package com.ftp.client.DTO;

import org.apache.commons.net.ftp.FTPFile;

/** Класс-обёртка для директорий, содержащий в себе саму директорию и путь к ней **/

public class FtpFolder {
    private FTPFile file;
    private String path;

    public FtpFolder(FTPFile file, String path) {
        this.file = file;
        this.path = path;
    }

    public FTPFile getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }


}
