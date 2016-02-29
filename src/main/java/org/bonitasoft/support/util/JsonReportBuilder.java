package org.bonitasoft.support.util;

import java.io.File;
import java.io.IOException;

import org.bonitasoft.support.reports.Reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Laurent Leseigneur
 */
public class JsonReportBuilder {

    private final Reporter reporter;

    public JsonReportBuilder(Reporter reporter) {
        this.reporter = reporter;
    }

    public String getJsonReport() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reporter);

    }

    public void getJsonFileReport(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, reporter);
    }
}
