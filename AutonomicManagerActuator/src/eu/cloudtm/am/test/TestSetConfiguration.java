package eu.cloudtm.am.test;

import eu.cloudtm.am.client.ActuatorClient;


public class TestSetConfiguration {


    public static void main(String[] args){
     if(args== null || args.length != 2){
            System.out.println("Usage: java TestChangeCOnfiguration <Actuator Server IP> <Atuator Server Port>");
        }

        ActuatorClient client = new ActuatorClient(args[0], Integer.parseInt(args[1]));


            client.setConfiguration(1, 2);


    }
}
