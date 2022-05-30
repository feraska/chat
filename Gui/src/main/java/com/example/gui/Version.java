package com.example.gui;

import java.security.MessageDigest;
import java.util.Arrays;

public class Version {
    private String fileName;
    private int version = 1;
    private String digest;

    public Version(String fileName, String digest) {
        this.fileName = fileName;
        this.digest = digest;
    }

    private void setVersion() {
        version++;
    }

    private void setDigest(String digest) {
        if (!(this.digest.equals(digest))) {
            this.digest = digest;
            setVersion();
        }
    }

    public String sha(byte[] msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(msg);
            return new String(bytes);
        } catch (Exception e) {

        }
        return "";
    }
}
