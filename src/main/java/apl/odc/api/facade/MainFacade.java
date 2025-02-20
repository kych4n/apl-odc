package apl.odc.api.facade;

import apl.odc.api.dto.request.InfoForFilteringRequest;
import apl.odc.api.dto.request.MappingInfoDTO;
import apl.odc.api.dto.response.AttributeResponse;
import apl.odc.api.dto.response.SignedUrlResponse;
import apl.odc.global.util.CSVFileReader;
import apl.odc.global.util.Encryptor;
import apl.odc.global.util.FileDownloader;
import apl.odc.global.util.SavePath;
import apl.odc.global.util.s3.S3Handler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainFacade {

    private final CSVFileReader csvFileReader;
    private final FileDownloader fileDownloader;
    private final S3Handler s3Handler;
    private final Encryptor encryptor;

    public AttributeResponse getAttributes() {
        List<String> header = csvFileReader.getHeader();
        return AttributeResponse.of(header);
    }

    public void prepareForFiltering(InfoForFilteringRequest infoForFilteringRequest) throws IOException {
        fileDownloader.downloadFiles(infoForFilteringRequest.frameUrl(), SavePath.FRAME.getPath());
        fileDownloader.downloadFiles(infoForFilteringRequest.ruleUrl(), SavePath.RULE.getPath());

        convert(infoForFilteringRequest.mappingInfo());
    }

    public void filter() {
        try {
            ProcessBuilder buildProcessBuilder = new ProcessBuilder("./gradlew", "clean", "build");
            buildProcessBuilder.directory(new java.io.File(SavePath.NEW_BUILD.getPath()));
            buildProcessBuilder.inheritIO();

            Process buildProcess = buildProcessBuilder.start();
            int buildExitCode = buildProcess.waitFor();

            if (buildExitCode == 0) {
                ProcessBuilder runProcessBuilder = new ProcessBuilder("java", "-jar",
                        SavePath.NEW_BUILD.getPath() + "/build/libs/filtering.jar");
                runProcessBuilder.inheritIO();
                Process process = runProcessBuilder.start();
                process.waitFor();
            } else {
                log.info("Filtering Failed.");
            }

        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage());
        }
    }

    private void convert(MappingInfoDTO mappingInfoDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(SavePath.JSON.getPath()), mappingInfoDTO);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void encrypt(Long userId) throws Exception {
        SecretKey secretKey = encryptor.generateKey(userId);
        encryptor.encrypt(secretKey);
    }

    public SignedUrlResponse getSignedUrl() {
        s3Handler.upload();
        return SignedUrlResponse.of(s3Handler.getSignedGetUrl());
    }

}
