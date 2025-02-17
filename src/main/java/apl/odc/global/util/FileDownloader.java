package apl.odc.global.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileDownloader {

    public void downloadFiles(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        String zipFileName = Paths.get(url.getPath()).getFileName().toString();

        try (InputStream inputStream = url.openStream()) {
            Path path = Paths.get(savePath, zipFileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }

        File zipFile = new File(savePath, zipFileName);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(zipFile));
        ZipInputStream zipInputStream = new ZipInputStream(in);
        ZipEntry zipEntry;

        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.isDirectory()) {
                continue;
            }

            int size;
            String fullName = zipEntry.getName();
            String fileName = fullName.substring(fullName.indexOf('/') + 1);
            File file = new File(savePath, fileName);

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            while ((size = zipInputStream.read()) != -1) {
                out.write(size);
                out.flush();
            }
            zipInputStream.closeEntry();
        }

        in.close();

        Files.deleteIfExists(Path.of(zipFile.getPath()));
    }

}
