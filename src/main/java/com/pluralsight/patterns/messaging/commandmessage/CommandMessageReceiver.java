package com.pluralsight.patterns.messaging.commandmessage;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * This is the receiver responsible for retrieving the command messages off the
 * queue and then invoking the appropriate command.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class CommandMessageReceiver {

   private static final Logger log = LoggerFactory
         .getLogger(CommandMessageReceiver.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private static final String QUEUE_NAME = "CommandMessageQueue";

   public String receive() {
      Connection connection = null;
      Channel channel = null;
      CommandInvoker commandInvoker = new CommandInvoker();
      int count = 0;
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
            String commandMessage = new String(delivery.getBody());
            responseMessage += commandInvoker.invoke(commandMessage) + "\n";
            count++;
            if (count == 2) {
               stopReceiving = true;
            }
         }
      } catch (Exception e) {
         log.error("An error occurred while sending a command message: " + e.getMessage(),
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
