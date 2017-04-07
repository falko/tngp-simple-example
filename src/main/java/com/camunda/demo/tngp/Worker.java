package com.camunda.demo.tngp;


import java.time.Duration;
import java.util.Properties;

import org.camunda.tngp.client.TngpClient;
import org.camunda.tngp.client.impl.TngpClientImpl;
import org.camunda.tngp.client.task.TaskSubscription;

public class Worker {

  public static void main(String[] args) {
    TngpClient client = new TngpClientImpl(new Properties());
    client.connect();

    TaskSubscription subscription = client.taskTopic(0) //
        .newTaskSubscription() //
        .taskType("SysoutTask") //
        .lockOwner(0) //
        .lockTime(Duration.ofMinutes(1)) //
        .handler((task) -> {
          System.out.println(task.getHeaders().get("message"));
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
