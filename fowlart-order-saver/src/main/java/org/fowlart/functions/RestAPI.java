package org.fowlart.functions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestAPI {
    public static void main(String[] args) {
        String order = "Order: \n" + "id: 1\n" + "name: test\n" + "price: 100\n";
        String connectionString = "DefaultEndpointsProtocol=https;AccountName=sepeus1lowerdpsa02;AccountKey=g9HczU2S6bzARkAlM/9uUBILxgN/oxNptoS1riudG+XrMFipB9LdJv6NtiqwT9R8JcouiR5y370q+ASt3nT4wA==;EndpointSuffix=core.windows.net";
        String containerName = "dplowersftp";
        String blobName = "qa/datadrive/dp.qa.gmt/outbound/Canada/" + System.currentTimeMillis() + "order.txt";

        try {
            // Create the blob URL
            String blobUrl = String.format("https://%s.blob.core.windows.net/%s/%s",
                    "sepeus1lowerdpsa02", containerName, blobName);

            // Create a URL object
            URL url = new URL(blobUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to PUT
            connection.setRequestMethod("PUT");

            // Set request headers
            connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
            connection.setRequestProperty("x-ms-blob-content-type", "application/pgp-encrypted");
            connection.setRequestProperty("Content-Length", String.valueOf(order.getBytes(StandardCharsets.UTF_8).length));
            connection.setRequestProperty("Authorization", "Bearer <YOUR_ACCESS_TOKEN>");

            // Enable input and output streams
            connection.setDoOutput(true);

            // Write the blob data
            connection.getOutputStream().write(order.getBytes(StandardCharsets.UTF_8));

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Blob uploaded successfully.");
            } else {
                System.out.println("Blob upload failed. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
