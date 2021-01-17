package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/library")
public class LibraryController {

    // endpoint pro získání zip souboru dle jeho názvu
    @RequestMapping(path = "/download/{name}", method = RequestMethod.GET)
    //public Resource download(@PathVariable(value = "name") String libraryName) {
    public ResponseEntity<Resource> download(@PathVariable(value = "name") String libraryName) {
        if (libraryName.equals("LibraryForOfflineMode")) {
            System.out.println("TOTU");
            File fileToDownload = new File("library/LibraryForOfflineMode.zip");
            Path path = Paths.get(fileToDownload.getAbsolutePath());
            ByteArrayResource resource = null;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
		//HttpHeaders header = new HttpHeaders();
		//header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LibraryForOfflineMode.zip");
            return ResponseEntity.ok()
		            .contentLength(fileToDownload.length())
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);
            //return resource;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Knihovna nebyla nalezena");
        }
    }
}
