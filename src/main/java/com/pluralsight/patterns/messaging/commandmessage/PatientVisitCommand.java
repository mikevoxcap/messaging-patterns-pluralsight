package com.pluralsight.patterns.messaging.commandmessage;

import java.util.*;

/**
 * Interface for commands related to patient visits.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
public interface PatientVisitCommand {

   /**
    * Sets parameters on the command.
    * 
    * @param parameters
    */
   void setParameters(Map<String, String> parameters);

   /**
    * Executes the command and return a response of what the command did.
    * 
    * @return String
    */
   String execute();
}
