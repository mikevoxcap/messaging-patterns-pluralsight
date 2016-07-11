package com.pluralsight.patterns.messaging.commandmessage;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rabbitmq.client.*;

/**
 * This is a sender responsible for packaging the command message and sending it
 * to the appropriate queue to be processed by a receiver. The sender is a
 * health care provider. The receiver is a health care payer. The sender needs
 * to know what commands are available from the receiver. In this case, the
 * sender will send a command to initialize a patient visit, then send a command
 * to end the patient visit.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@Component
public class CommandMessageSender {

   private static final Logger log = LoggerFactory.getLogger(CommandMessageSender.class);

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   private String startPatientVisitMessage = "{\"name\":\"StartPatientVisit\","
         + "\"parameters\":{" + "\"date\":\"05/01/2016 11:01:20\","
         + "\"providerId\":\"aa-123\","
         + "\"concern\":\"Headache and upset stomach\",\"memberId\":\"12345\"}}";
   private String endPatientVisitMessage = "{\"name\":\"EndPatientVisit\","
         + "\"parameters\":{" + "\"date\":\"05/01/2016 12:01:20\","
         + "\"providerId\":\"aa-123\","
         + "\"diagnosis\":\"Cold\",\"memberId\":\"12345\"}}";
   private static final String QUEUE_NAME = "CommandMessageQueue";

   /**
    * Sends two messages, one for the start patient visit command and one for
    * the end patient visit command.
    */
   public void send() {
      Connection connection = null;
      Channel channel = null;

      try {
         connection = rabbitMqConnectionFactory.newConnection();
         channel = connection.createChannel();
         channel.queueDeclare(QUEUE_NAME, true, false, false, null);
         channel.basicPublish("", QUEUE_NAME, null, startPatientVisitMessage.getBytes());
         channel.basicPublish("", QUEUE_NAME, null, endPatientVisitMessage.getBytes());
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
   }
}
