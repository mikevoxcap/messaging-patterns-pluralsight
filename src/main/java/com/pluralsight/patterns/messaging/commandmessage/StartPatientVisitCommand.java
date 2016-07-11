package com.pluralsight.patterns.messaging.commandmessage;

import java.util.*;

/**
 * Command for the start of a patient visit.
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
public class StartPatientVisitCommand implements PatientVisitCommand {

   private String date;
   private String providerId;
   private String concern;
   private String memberId;

   @Override
   public void setParameters(Map<String, String> parameters) {
      this.date = parameters.get("date");
      this.providerId = parameters.get("providerId");
      this.concern = parameters.get("concern");
      this.memberId = parameters.get("memberId");
   }

   @Override
   public String execute() {
      return "Executing the StartPatientVisitCommand with the date |" + date
            + "|, providerId |" + providerId + "|, concern |" + concern
            + "| and memberId |" + memberId + "|";
   }

}
