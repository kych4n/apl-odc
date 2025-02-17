package apl.odc.global.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CSVFileReader {

    private final CSVReader csvReader;
    private final List<String> header;

    public CSVFileReader() throws IOException, CsvValidationException {
        this.csvReader = new CSVReader(new FileReader(SavePath.ORIGINAL.getPath()));
        this.header = Arrays.asList(csvReader.readNext());
    }

    public List<String> getHeader() {
        return header.stream().toList();
    }

}
