package com.social.net.common.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static String saveDir = "src/file_storage";

    public static String saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || StringUtil.checkNullOrEmpty(multipartFile.getOriginalFilename())) {
            return null;
        }

        String savePath = String.format("%s/%s", saveDir,
                StringUtil.generateFilename(multipartFile.getOriginalFilename()));
        File newFile = new File(savePath);
        boolean success = newFile.createNewFile();
        if (success) {
            try (OutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write(multipartFile.getBytes());
            }
            return savePath;
        }
        return null;
    }

    public static List<String> saveMultipleFile(List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        urls = multipartFiles.stream().map((multipartFile) -> {
            String savePath = String.format("%s/%s", saveDir,
                    StringUtil.generateFilename(multipartFile.getOriginalFilename()));
            File newFile = new File(savePath);
            boolean success = false;
            try {
                success = newFile.createNewFile();
                if (success) {
                    try (OutputStream outputStream = new FileOutputStream(newFile)) {
                        outputStream.write(multipartFile.getBytes());
                    }
                    return savePath;
                }
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }).collect(Collectors.toList());

        return urls;
    }

    public static void deleteFile(String url) throws IOException {
        Files.delete(Paths.get(url));
    }

    public static Resource loadFile(String url) throws IOException {
        File file = new File(url);
        return new ByteArrayResource(Files.readAllBytes(file.toPath()));
    }
}
