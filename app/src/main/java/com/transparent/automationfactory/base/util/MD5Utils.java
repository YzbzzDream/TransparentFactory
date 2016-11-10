package com.transparent.automationfactory.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


public class MD5Utils {

    public static final String SECRET_NEW = "4r9ti+mctJQ-FWb*o3cFZUaa4g";
    private static final String SECRET = "Automation2016";
    private static final String SECRET_KEY = "-qmYE8En^S9F0x$pG&6y=m8J+4RSvVuC";

    public static String compute(String content) throws Exception {
        if (content.isEmpty())
            return null;
        MessageDigest digestInstance = MessageDigest.getInstance("MD5");
        digestInstance.update(content.getBytes("utf-8"));
        byte[] md = digestInstance.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = (md[i]) & 0xff;
            if (val < 16)
                sb.append("0");
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toUpperCase();
    }

    public static String validate(String... params) {
        String valueData = "";
        StringBuilder sb = new StringBuilder();
        if (null != params) {
            for (String param : params) {
                sb.append(param);
            }
        } else {
            sb.append("");
        }
        sb.append(SECRET_KEY);
        try {
            valueData = compute(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueData;
    }

    public static String validate(Map<String, String> param) {
        return validate(param, SECRET);
    }

    public static String validate(Map<String, String> param, String secret) {
        String valuedata = "";

        StringBuilder builder = new StringBuilder();

        for (String paramName : param.keySet()) {
            String str = param.get(paramName);
            builder.append(str);
        }

        builder.append(secret);

        try {
            valuedata = compute(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valuedata;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}
