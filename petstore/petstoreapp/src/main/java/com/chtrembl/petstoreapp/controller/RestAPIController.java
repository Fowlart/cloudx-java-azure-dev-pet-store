package com.chtrembl.petstoreapp.controller;

import com.azure.storage.common.ParallelTransferOptions;
import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import com.azure.storage.file.share.ShareServiceClientBuilder;
import com.chtrembl.petstoreapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * REST controller to facilitate REST calls such as session keep alives
 * (progressive web apps)
 */
@RestController
public class RestAPIController {

    private static final Logger logger = Logger.getLogger(RestAPIController.class.getName());
    @Autowired
    private User sessionUser;

    @GetMapping("/api/contactus")
    public String contactus() {

        logger.info("PetStoreApp user " + this.sessionUser.getName() + " requesting Contact Us");

        this.sessionUser.getTelemetryClient().trackEvent(String.format("PetStoreApp user %s requesting Contact Us", this.sessionUser.getName()), this.sessionUser.getCustomEventProperties(), null);

        return "Please contact Azure PetStore at 401-555-5555. Thank you. Demo 6/13";
    }

    @GetMapping("/api/sessionid")
    public String sessionid() {

        logger.info("PetStoreApp user " + this.sessionUser.getName() + " requesting Session ID");

        return this.sessionUser.getSessionId();
    }

    @GetMapping(value = "/introspectionSimulation", produces = MediaType.APPLICATION_JSON_VALUE)
    public String introspectionSimulation(Model model, HttpServletRequest request, @RequestParam(name = "sessionIdToIntrospect") Optional<String> sessionIdToIntrospect) {

        logger.info("PetStoreApp user " + this.sessionUser.getName() + " requesting Introspection Simulation");

        boolean active = (sessionIdToIntrospect != null && sessionIdToIntrospect.isPresent() && sessionIdToIntrospect.get() != null && sessionIdToIntrospect.get().equals(request.getHeader("session-id")));

        return "{\n" + "  \"active\": " + active + ",\n" + "  \"scope\": \"read write email\",\n" + "  \"client_id\": \"" + request.getHeader("session-id") + "\",\n" + "  \"username\": \"" + request.getHeader("session-id") + "\",\n" + "  \"exp\": 1911221039\n" + "}";
    }

    @GetMapping(value = "/api/generate_file", produces = MediaType.APPLICATION_JSON_VALUE)
    public String connectToFowlartFileShare(@RequestParam(name = "text") String text) {

        logger.info("PetStoreApp user " + this.sessionUser.getName() + " started connecting to FowlArt File Share.");

        // get environment variable for connection string
        String connectionString = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
        String shareName = "botstore";
        String directoryName = "received_files";
        String fileName = System.currentTimeMillis() + "note.txt";

        ShareServiceClient shareServiceClient = new ShareServiceClientBuilder().connectionString(connectionString).buildClient();
        ShareClient shareClient = shareServiceClient.getShareClient(shareName);
        ShareFileClient fileClient = shareClient.getDirectoryClient(directoryName).getFileClient(fileName);
        fileClient.create(text.getBytes().length);
        //write text to file
        ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions();
        var res = fileClient.upload(new ByteArrayInputStream(text.getBytes()), text.length(), parallelTransferOptions);
        return res.getETag();
    }
}