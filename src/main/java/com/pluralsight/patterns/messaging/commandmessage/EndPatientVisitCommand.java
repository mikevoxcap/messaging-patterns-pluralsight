package com.pluralsight.patterns.messaging.commandmessage;

import java.util.*;

/**
 * Command for the end of a patient visit.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
public class EndPatientVisitCommand implements PatientVisitCommand {

   private String date;
   private String providerId;
   private String diagnosis;
   private String memberId;

   @Override
   public void setParameters(Map<String, String> parameters) {
      this.date = parameters.get("date");
      this.providerId = parameters.get("providerId");
      this.diagnosis = parameters.get("diagnosis");
      this.memberId = parameters.get("memberId");
   }

   @Override
   public String execute() {
      return "Executing the EndPatientVisitCommand with the date |" + date
            + "|, providerId |" + providerId + "|, diagnosis |" + diagnosis
            + "| and memberId |" + memberId + "|";
   }

}
