package com.pluralsight.patterns.messaging.eventmessage;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * Sends an event message to be processed by one or more observers.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class EventMessageSender {

   private static final Log log = LogFactory.getLog(EventMessageSender.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private String eventMessage = "{\"eventName\":\"LowGlucoseEpisode\", "
         + "\"deviceId\":\"AA382-2838433\", " + "\"memberId\":\"1234\"}";

   private static final String EXCHANGE_NAME = "fanout_episodes";

   /**
    * Sends the event message to all subscribers of the event.
    */
   public void send() {
      Connection connection = null;
      Channel channel = null;

      try {
         connection = rabbitMqConnectionFactory.newConnection();
         channel = connection.createChannel();
         // channel.exchangeDeclare(EXCHANGE_NAME, "direct");
         channel.basicPublish(EXCHANGE_NAME, "", null, eventMessage.getBytes());
      } catch (Exception e) {
         log.error("An error occurred while sending an event message: " + e.getMessage(),
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
