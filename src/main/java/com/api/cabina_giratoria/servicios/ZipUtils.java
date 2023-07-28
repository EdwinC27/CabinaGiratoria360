package com.api.cabina_giratoria.servicios;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipUtils {

    public static void zipDirectory(File sourceDirectory, File zipFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        zipDirectoryToZipOut(sourceDirectory, sourceDirectory.getName(), zipOut);

        zipOut.close();
        fos.close();
    }

    private static void zipDirectoryToZipOut(File directory, String baseName, ZipOutputStream zipOut) throws IOException {
        byte[] buffer = new byte[1024];
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    zipDirectoryToZipOut(file, baseName + File.separator + file.getName(), zipOut);
                } else {
                    FileInputStream fis = new FileInputStream(file);
                    zipOut.putNextEntry(new ZipEntry(baseName + File.separator + file.getName()));

                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, length);
                    }

                    zipOut.closeEntry();
                    fis.close();
                }
            }
        }
    }

}
