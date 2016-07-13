package com.pluralsight.patterns.messaging.eventmessage;

import java.io.*;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * Receiver acting as an observer to event notifications from publish-subscribe
 * channel.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class EventMessageReceiver1 {

   private static final Log log = LogFactory.getLog(EventMessageReceiver1.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private static final String QUEUE_NAME = "PhysicianA.LowGlucoseEpisode";

   public void receive() {
      Connection connection = null;
      Channel channel = null;

      try {
         connection = rabbitMqConnectionFactory.newConnection();
         channel = connection.createChannel();
         Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                  AMQP.BasicProperties properties, byte[] body) throws IOException {
               System.out.println("Receiver 1: " + new String(body));
            }
         };
         channel.basicConsume(QUEUE_NAME, true, consumer);
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
