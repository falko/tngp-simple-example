package com.camunda.demo.tngp;

import java.util.Properties;

import org.camunda.tngp.client.TngpClient;
import org.camunda.tngp.client.event.EventMetadata;
import org.camunda.tngp.client.event.TopicEvent;
import org.camunda.tngp.client.event.TopicEventHandler;
import org.camunda.tngp.client.event.TopicSubscription;

public class ReadEvents {

  public static void main(String[] args) throws InterruptedException {
    TngpClient client = TngpClient.create(new Properties());
    client.connect();

    TopicSubscription subscription = client.taskTopic(0) //
        .newSubscription() //
        .startAtHeadOfTopic() //
        .name("cmd-line-reader") //
        .defaultHandler(new TopicEventHandler() {

          @Override
          public void handle(EventMetadata metadata, TopicEvent event) throws Exception {
            System.out.println(event.getJson());
          }

        }).open();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        subscription.close();
        client.disconnect();
        client.close();
      }
    });

  }

}
