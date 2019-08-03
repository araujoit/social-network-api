package br.com.araujo.socialnetwork.resources;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Path("/api")
public class ApiResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get() throws IOException {
        return readHtmlFileContent("api");
    }

    private String readHtmlFileContent(String htmlFileName) throws IOException {
        try (final InputStream inputStream = readHtmlResource(htmlFileName)) {
            byte[] byteContent = new byte[inputStream.available()];
            IOUtils.readFully(inputStream, byteContent);
            return new String(byteContent, StandardCharsets.UTF_8);
        }
    }

    private InputStream readHtmlResource(String htmlFileName) {
        final String fileName = htmlFileName.endsWith(".html") ? htmlFileName : htmlFileName.concat(".html");
        return readResource(fileName);
    }

    private InputStream readResource(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
