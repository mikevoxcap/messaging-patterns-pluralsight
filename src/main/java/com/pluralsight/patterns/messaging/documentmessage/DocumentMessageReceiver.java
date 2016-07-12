package com.pluralsight.patterns.messaging.documentmessage;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * This is the receiver responsible for retrieving the document message off the
 * queue.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class DocumentMessageReceiver {

   private static final Logger log = LoggerFactory
         .getLogger(DocumentMessageReceiver.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private static final String QUEUE_NAME = "DocumentMessageQueue";

   public String receive() {
      Connection connection = null;
      Channel channel = null;
      String responseMessage = "";

      try {
         connection = rabbitMqConnectionFactory.newConnection();
         channel = connection.createChannel();
         channel.queueDeclare(QUEUE_NAME, true, false, false, null);
         QueueingConsumer consumer = new QueueingConsumer(channel);
         channel.basicConsume(QUEUE_NAME, true, consumer);

         boolean stopReceiving = false;
         while (!stopReceiving) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String documentMessage = new String(delivery.getBody());
            responseMessage += documentMessage + "\n";
            stopReceiving = true;
         }
      } catch (Exception e) {
         log.error(
               "An error occurred while sending a document message: " + e.getMessage(),
               e);
      } finally {
         if (channel != null) {
            try {
               channel.close();
            } catch (Exception e) {
            }
         }
         if (connection != null) {
            try {
               connection.close();
            } catch (Exception e) {
            }
         }
      }

      return responseMessage;
   }
}
