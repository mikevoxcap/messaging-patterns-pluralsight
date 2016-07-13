package com.pluralsight.patterns.messaging.eventmessage;

import org.apache.commons.logging.*;
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
public class EventMessageApi {

   private static final Log log = LogFactory.getLog(EventMessageApi.class);

   @Autowired
   private EventMessageSender eventMessageSender;

   @Autowired
   private EventMessageReceiver1 eventMessageReceiver1;

   @Autowired
   private EventMessageReceiver1 eventMessageReceiver2;

   /**
    * Sends the event message. You should have all receivers "started" before
    * calling this to verify.
    * 
    * @return
    */
   @RequestMapping(value = "/eventMessage", method = RequestMethod.GET,
         produces = "text/plain")
   public String runEventMessage() {
      eventMessageSender.send();
      return "The event message was sent.";
   }

   /**
    * Starts receiver 1.
    * 
    * @return
    */
   @RequestMapping(value = "/eventMessageReceiver1", method = RequestMethod.GET,
         produces = "text/plain")
   public String runReceiver1() {
      return "Receiver 1 is running";
   }

   /**
    * Starts receiver 2
    * 
    * @return
    */
   @RequestMapping(value = "/eventMessageReceiver2", method = RequestMethod.GET,
         produces = "text/plain")
   public String runReceiver2() {
      return "Receiver 2 is running";
   }

}
