package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

/**
 * @author Bc. Ondřej Schneider - FIM UHK
 * @version 1.0
 * @since 2021-04-02
 * Controller (API) pro stažení externích knihoven z centrálního serveru
 */
@RestController
@RequestMapping("/library")
public class LibraryController {

    // endpoint pro získání zip souboru dle jeho názvu
    @RequestMapping(path = "/download/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable(value = "name") String libraryName) {
        if (libraryName.equals("LibraryForOfflineMode")) {
            System.out.println("stahování knihovny LibraryForOfflineMode....");
            File fileToDownload = new File("library/LibraryForOfflineMode.zip");
            return downloadLibraryByFile(fileToDownload);

        } else if (libraryName.equals("LibraryForP2PCommunication")) {
            System.out.println("stahování knihovny LibraryForP2PCommunication....");
            File fileToDownload = new File("library/LibraryForP2PCommunication.zip");
            return downloadLibraryByFile(fileToDownload);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Knihovna nebyla nalezena");
        }
    }

    // metoda pro stažení knihovny dle souboru knihovny
    private ResponseEntity<Resource> downloadLibraryByFile(File fileToDownload) {
        Path path = Paths.get(fileToDownload.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentLength(fileToDownload.length())
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }
}
