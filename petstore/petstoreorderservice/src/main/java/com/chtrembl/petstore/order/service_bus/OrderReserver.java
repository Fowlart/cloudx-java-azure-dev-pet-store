package com.chtrembl.petstore.order.service_bus;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class OrderReserver {

    private final ServiceBusSenderClient senderClient;

    public OrderReserver(@Value("${servicebus.connection-string}") String connectionString,
                         @Value("${servicebus.queue.name}") String queueName) {

        this.senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();
    }

    public  void sendMessage(String msg) {

        var message = new ServiceBusMessage(msg);

        senderClient
                .sendMessage(message);
    }
    @PreDestroy
    public void destroy()
    {
        senderClient.close();
    }
}
