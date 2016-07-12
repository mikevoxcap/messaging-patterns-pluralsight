package com.pluralsight.patterns.messaging.documentmessage;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * This is the API for demonstrating the execution of the document message
 * pattern demo.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@RestController
@RequestMapping("/api")
public class DocumentMessageApi {

   @Autowired
   private DocumentMessageSender documentMessageSender;

   @Autowired
   private DocumentMessageReceiver documentMessageReceiver;

   /**
    * Runs the sender and receiver for the document message. Returns details of
    * the execution.
    * 
    * @return
    */
   @RequestMapping(value = "/documentMessage", method = RequestMethod.GET,
         produces = "text/plain")
   public String runDocumentMessage() {
      // First, send the message.
      documentMessageSender.send();
      // Then, get the response from the queue to verify processing
      String response = documentMessageReceiver.receive();
      return "Successfully processed the document message with the following output:\n\n"
            + response;
   }
}
