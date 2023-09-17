package petstore.order.reservation;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.io.ByteArrayInputStream;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class ServiceBusQueueTriggerJava {
    /**
     * This function will be invoked when a new message is received at the Service Bus Queue.
     */
    @FunctionName("OrderReservationFunction")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "pet_store_order_queue",
                    connection = "ServiceBusConnectionString") String message,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Service Bus Queue trigger function executed.");
        context.getLogger().info(message);

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(System.getenv("BLOB_CONNECTION_STRING")).buildClient();

        BlobContainerClient containerClient =
                blobServiceClient.createBlobContainerIfNotExists("orders");

        String blobName = System.currentTimeMillis() + "order.txt";

        BlobClient blobClient = containerClient.getBlobClient(blobName);

        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType("text/plain");

        blobClient.upload(new ByteArrayInputStream(message.getBytes()), true);

        blobClient.setHttpHeaders(headers);

        if (blobClient.exists()) {
            context.getLogger().info("Blob was created");
        }
        else {
            // fail the function
            throw new RuntimeException("Blob was not created");
        }
    }
}
