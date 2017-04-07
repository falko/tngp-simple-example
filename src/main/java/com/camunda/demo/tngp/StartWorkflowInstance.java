package com.camunda.demo.tngp;


import java.util.Properties;

import org.camunda.tngp.client.TngpClient;
import org.camunda.tngp.client.WorkflowTopicClient;
import org.camunda.tngp.client.impl.TngpClientImpl;
import org.camunda.tngp.client.workflow.cmd.WorkflowInstance;

public class StartWorkflowInstance {

  public static void main(String[] args) {
    TngpClient client = new TngpClientImpl(new Properties());
    client.connect();

    WorkflowTopicClient workflowTopic = client.workflowTopic(0);

    // start 5 workflow instances
    for (int i = 0; i < 5; i++) {
      WorkflowInstance workflowInstance = workflowTopic.create() //
          .bpmnProcessId("serviceTaskHeader") //
          .execute();
      System.out.println("started " + workflowInstance.getWorkflowInstanceKey());
    }

    client.disconnect();
    client.close();
  }

}
