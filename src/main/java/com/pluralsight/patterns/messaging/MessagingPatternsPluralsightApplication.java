package com.pluralsight.patterns.messaging;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

import com.rabbitmq.client.*;

/**
 * The spring boot app
 * 
 * @author Michael Hoffman, Pluralsight
 *
 */
@SpringBootApplication
public class MessagingPatternsPluralsightApplication {

   @Value("${rabbitmq.uri}")
   private String rabbitMqUri;

   public static void main(String[] args) {
      SpringApplication.run(MessagingPatternsPluralsightApplication.class, args);
   }

   @Bean
   public ConnectionFactory rabbitMqConnectionFactory() throws Exception {
      ConnectionFactory connectionFactory = new ConnectionFactory();
      connectionFactory.setUri(rabbitMqUri);
      return connectionFactory;
   }

}
