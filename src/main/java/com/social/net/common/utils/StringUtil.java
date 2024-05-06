package com.social.net.common.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class StringUtil {
    public static boolean checkNullOrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean checkNotNullOrEmpty(String str) {
        return (str != null && !str.trim().isEmpty());
    }

    public static String getToken(String token) {
        if (checkNotNullOrEmpty(token)) {
            return token.substring(7);
        }
        return null;
    }

    public static String generateFilename(String originFilename) {
        if (checkNullOrEmpty(originFilename))
            return "";
        String filename = originFilename.substring(0,
                originFilename.lastIndexOf("."));
        String fileExtension = StringUtils.getFilenameExtension(originFilename);
        return String.format("%s-%s.%s", filename, UUID.randomUUID().toString(), fileExtension);
    }
}
