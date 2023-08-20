package org.fowlart.functions;

import java.io.ByteArrayInputStream;
import java.util.*;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class OrderSaver {
    /**
     * This function listens at endpoint "/api/save_order".
     */
    @FunctionName("save_order")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        Optional<String> orderOptional = request.getBody();

        if (!orderOptional.isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Object i").build();
        } else {

            String order = orderOptional.get();

            context.getLogger().info("saving Order: \n" + order);

            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(System.getenv("BLOB_CONNECTION_STRING")).buildClient();

            BlobContainerClient containerClient =
                    blobServiceClient.createBlobContainerIfNotExists("orders");

            String blobName = System.currentTimeMillis() + "order.txt";

            BlobClient blobClient = containerClient.getBlobClient(blobName);

            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType("text/plain");

            blobClient.upload(new ByteArrayInputStream(order.getBytes()), true);

            blobClient.setHttpHeaders(headers);


            return request.createResponseBuilder(HttpStatus.OK).body("Order was created in the storage!").build();
        }
    }
}
