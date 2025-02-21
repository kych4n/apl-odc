package apl.odc.global.util.s3;

import apl.odc.global.util.SavePath;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Handler {

    private final S3Operations s3Operations;
    private final S3Properties s3Properties;
    private final Duration duration = Duration.ofMinutes(3L);

    public void upload() {
        File file = new File(SavePath.ENCRYPTED.getPath());
        Path path = file.toPath();
        try (InputStream is = Files.newInputStream(path)) {
            s3Operations.upload(s3Properties.bucketName(), s3Properties.dataKey(), is,
                    ObjectMetadata.builder().contentType(Files.probeContentType(path)).build());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public URL getSignedGetUrl() {
        return s3Operations.createSignedGetURL(s3Properties.bucketName(), s3Properties.dataKey(), duration);
    }

    public void remove() {
        if (s3Operations.objectExists(s3Properties.bucketName(), s3Properties.dataKey())) {
            s3Operations.deleteObject(s3Properties.bucketName(), s3Properties.dataKey());
        }
    }

}
