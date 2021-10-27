package com.techhype.digitalinventory.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class FileUtils {
    public static ByteArrayInputStream getInputStreamFromBase64(String base64File) {
        String file = base64File;
        if (base64File.split(",").length > 1) {
            file = base64File.split(",")[1];
        }
        byte[] decbytes = Base64.getDecoder().decode(file);
        return new ByteArrayInputStream(decbytes);
    }

    public static byte[] getByteArrayFromInputStream(InputStream is) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

    public static byte[] getByteArrayFromBase64File(String file) throws IOException {

        return getByteArrayFromInputStream(getInputStreamFromBase64(file));
    }
}
