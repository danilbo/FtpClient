package com.ftp.client.DTO;

import org.apache.commons.codec.binary.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/** Класс содержащий информацию о файле: имя, путь, дата, размеры файла (в байтах)*/
public class FileInfo {
    private String name;
    private String path;
    private LocalDateTime date;
    private long size;

    public FileInfo(String name,String path, Calendar calendar, long size) {
        this.name = name;
        this.path = recodePath(path);
        this.date = getLocalDateTime(calendar);
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getSize() {
        return size;
    }

    private LocalDateTime getLocalDateTime(Calendar calendar){
        TimeZone tz = calendar.getTimeZone();
        ZoneId zoneId = tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
    }

    private String recodePath(String path){
        byte[] bytes = StringUtils.getBytesIso8859_1(path);
        String result = StringUtils.newStringUtf8(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "{Name: " + name + " Path: " + path + " Date: " + date + " Size: " + size + " Bytes}" + "\n";
    }
}
