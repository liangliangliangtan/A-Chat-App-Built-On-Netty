package com.example.mychatappnetty.entity.dto;

import org.springframework.web.multipart.MultipartFile;


import java.io.*;

/**
 * Personal-defined MultipartFile
 */
public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    private final String name;
    private final String originalFileName; // the extension name, starting with dot, such like ".png";

    public BASE64DecodedMultipartFile(String name,String originalFileName,byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header;
        this.name = name;
        this.originalFileName = originalFileName;
    }


    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return this.name;

    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return this.originalFileName;

    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(imgContent);
    }
}
