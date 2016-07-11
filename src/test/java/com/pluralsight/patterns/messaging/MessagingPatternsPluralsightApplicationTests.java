package com.pluralsight.patterns.messaging;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;

import com.rabbitmq.client.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MessagingPatternsPluralsightApplication.class)
@WebAppConfiguration
public class MessagingPatternsPluralsightApplicationTests {

   @Autowired
   private ConnectionFactory rabbitMqConnectionFactory;

   @Test
   public void contextLoads() {
      assertNotNull(rabbitMqConnectionFactory);
   }

}
