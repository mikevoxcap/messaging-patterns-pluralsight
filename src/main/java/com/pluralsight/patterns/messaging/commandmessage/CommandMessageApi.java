package com.pluralsight.patterns.messaging.commandmessage;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * This is the API for demonstrating the execution of the command message
 * pattern demo.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@RestController
@RequestMapping("/api")
public class CommandMessageApi {

   @Autowired
   private CommandMessageSender commandMessageSender;

   @Autowired
   private CommandMessageReceiver commandMessageReceiver;

   /**
    * Runs the sender and receiver for the command message. Returns details of
    * the execution.
    * 
    * @return
    */
   @RequestMapping(value = "/commandMessage", method = RequestMethod.GET,
         produces = "text/plain")
   public String runCommandMessage() {
      // First, send the messages.
      commandMessageSender.send();
      // Then, get the responses from the queue and execute the commands.
      String response = commandMessageReceiver.receive();
      return "Successfully processed command messages with the following output:\n\n"
            + response;
   }
}
