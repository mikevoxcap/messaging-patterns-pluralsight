package com.pluralsight.patterns.messaging.commandmessage;

import java.util.*;

import org.slf4j.*;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

/**
 * Invokes the command passed.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
public class CommandInvoker {

   private static final Logger log = LoggerFactory.getLogger(CommandInvoker.class);

   /**
    * Invoke the command based on the JSON message passed. Need to get the name
    * and parameters first from the map then execute the command based on the
    * values.
    * 
    * @param commandMessage
    * @return
    */
   public String invoke(String commandMessage) {
      String response = "";
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         Map<String, Object> commandMessageMap = objectMapper.readValue(commandMessage,
               new TypeReference<Map<String, Object>>() {
               });
         String commandName = (String) commandMessageMap.get("name");
         Map<String, String> parameters = (Map<String, String>) commandMessageMap
               .get("parameters");
         PatientVisitCommand command = null;
         if (commandName.equals("StartPatientVisit")) {
            command = new StartPatientVisitCommand();
         } else if (commandName.equals("EndPatientVisit")) {
            command = new EndPatientVisitCommand();
         } else {
            throw new Exception("Unsupported command: " + commandName);
         }
         command.setParameters(parameters);
         response = command.execute();
      } catch (Exception e) {
         log.error("An error occurred while invoking the command: " + e.getMessage(), e);
      }

      return response;
   }

}
