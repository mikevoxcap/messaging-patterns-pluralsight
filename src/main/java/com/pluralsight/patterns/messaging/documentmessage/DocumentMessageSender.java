package com.pluralsight.patterns.messaging.documentmessage;

import java.nio.file.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * This is a sender responsible for sending the document message to the
 * appropriate queue to be processed by a receiver. The sender is a health care
 * provider. The receiver is a health care payer. The document is meant to
 * contain information about a patient visit that is relevant to the payer.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class DocumentMessageSender {

   private static final Logger log = LoggerFactory.getLogger(DocumentMessageSender.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private static final String QUEUE_NAME = "DocumentMessageQueue";

   /**
    * Sends the document message to the queue.
    */
   public void send() {
      Connection connection = null;
      Channel channel = null;

      try {
         byte[] invoicesXml = Files
               .readAllBytes(Paths.get(getClass().getResource("Invoice.xml").toURI()));
         connection = rabbitMqConnectionFactory.newConnection();
         channel = connection.createChannel();
         channel.queueDeclare(QUEUE_NAME, true, false, false, null);
         channel.basicPublish("", QUEUE_NAME, null, invoicesXml);
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
   }
}
