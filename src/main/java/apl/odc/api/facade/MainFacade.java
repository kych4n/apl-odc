package apl.odc.api.facade;

import apl.odc.api.dto.request.InfoForFilteringRequest;
import apl.odc.api.dto.response.AttributeResponse;
import apl.odc.global.util.CSVFileReader;
import apl.odc.global.util.FileDownloader;
import apl.odc.global.util.SavePath;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainFacade {

    private final CSVFileReader csvFileReader;
    private final FileDownloader fileDownloader;

    public AttributeResponse getAttributes() {
        List<String> header = csvFileReader.getHeader();
        return AttributeResponse.of(header);
    }

    public void prepareForFiltering(InfoForFilteringRequest infoForFilteringRequest) throws IOException {
        fileDownloader.downloadFiles(infoForFilteringRequest.frameUrl(), SavePath.FRAME.getPath());
        fileDownloader.downloadFiles(infoForFilteringRequest.ruleUrl(), SavePath.RULE.getPath());
    }
}
