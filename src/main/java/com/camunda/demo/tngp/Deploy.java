package com.camunda.demo.tngp;


import java.util.Properties;

import org.camunda.tngp.client.TngpClient;
import org.camunda.tngp.client.WorkflowTopicClient;
import org.camunda.tngp.client.impl.TngpClientImpl;
import org.camunda.tngp.client.workflow.cmd.DeploymentResult;

public class Deploy {

  public static void main(String[] args) {
    TngpClient client = new TngpClientImpl(new Properties());
    client.connect();
    WorkflowTopicClient workflowTopic = client.workflowTopic(0);

    DeploymentResult deploymentResult = workflowTopic.deploy() //
        .resourceStream(Deploy.class.getResourceAsStream("/serviceTaskHeader.bpmn")) //
        .execute();

    if (!deploymentResult.isDeployed()) {
      client.disconnect();
      client.close();
      throw new RuntimeException(deploymentResult.getErrorMessage());
    }

    System.out.println("Deployed");

    client.disconnect();
    client.close();
  }

}
