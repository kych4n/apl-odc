package apl.odc.api.facade;

import apl.odc.api.dto.response.AttributeResponse;
import apl.odc.global.util.CSVFileReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainFacade {

    private final CSVFileReader csvFileReader;

    public AttributeResponse getAttributes() {
        List<String> header = csvFileReader.getHeader();
        return AttributeResponse.of(header);
    }

}
