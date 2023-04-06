package com.ftp.client.repositories;

import com.ftp.client.DTO.FileInfo;
import com.ftp.client.DTO.FtpFolder;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@org.springframework.stereotype.Repository
public class ImgRepository {
    @Autowired
    private FtpClient ftpClient;

    /**
     * Метод для поиска ВСЕХ файлов во ВСЕХ директориях (не обязательно изображений)
     */
    public List<FileInfo> getAllFiles() throws IOException {
        /**Переменная для хранения обнаруживаемых директорий */
        Queue<FtpFolder> queue = new LinkedList<>();
        /**Переменная для хранения всех обнаруживаемых файлов */
        List<FileInfo> files = new ArrayList<>();
        /** Переменные для работы с текущей директорией (для хранения файлов из текущей директории и информации о ней */
        List<FTPFile> folderFiles;
        FtpFolder currentFolder;
        String currentPath;
        String tmpName;

        /** устанавливаем соединение с сервером */
        ftpClient.open();

        /** Пробегаем по корневой директории, найденные директории добовляем в очередь для дальнейшей работы,
         * файлы в список файлов */
        for (FTPFile f : ftpClient.getFilesFromDir("/")) {
            if (f.isDirectory()) {
                if (!f.getName().equals(".") && !f.getName().equals("..")) {
                    queue.add(new FtpFolder(f, ""));
                }
            } else {
                files.add(new FileInfo(f.getName(), "/" + f.getName(), f.getTimestamp(), f.getSize()));
            }
        }
        /** Пока в очереди есть директории, просматриваем директории, найденные директории добавляем в очередь,
         * файлы в список файлов */
        while (!queue.isEmpty()) {
            currentFolder = queue.poll();
            tmpName = currentFolder.getFile().getName();

            currentPath = currentFolder.getPath() + "/" + tmpName;
            folderFiles = Arrays.stream(ftpClient.getFilesFromDir(currentPath)).toList();
            for (FTPFile f : folderFiles) {
                if (f.isDirectory()) {
                    if (!f.getName().equals(".") && !f.getName().equals("..")) {
                        queue.add(new FtpFolder(f, currentPath));
                    }
                } else {
                    files.add(new FileInfo(f.getName(), currentPath + "/" + f.getName(), f.getTimestamp(), f.getSize()));
                }
            }
        }
        /** Закрываем соединение с сервером */
        ftpClient.close();

        return files;
    }


}
