package ann;

import java.io.File;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import eu.cloudtm.am.client.ActuatorClient;
import eu.cloudtm.wpm.logService.remote.events.PublishAttribute;
import eu.cloudtm.wpm.logService.remote.events.PublishMeasurement;
import eu.cloudtm.wpm.logService.remote.events.PublishStatisticsEvent;
import eu.cloudtm.wpm.logService.remote.listeners.WPMStatisticsRemoteListener;
import eu.cloudtm.wpm.connector.*;
import eu.cloudtm.wpm.parser.ResourceType;


public class NNStatisticsRemoteListenerImpl implements WPMStatisticsRemoteListener {
	
	private WPMConnector connector;
	private Nns nn;
	ActuatorClient ac;
	
	public NNStatisticsRemoteListenerImpl(WPMConnector connector, Nns nn, ActuatorClient ac){
		this.connector = connector;
		
		this.nn = nn;
		this.ac = ac;

	}


	@Override
	public void onNewPerVMStatistics(PublishStatisticsEvent event) throws RemoteException {

		System.out.println("Called onNewPerVMStatistics");

	}

	@Override
	public void onNewPerSubscriptionStatistics(PublishStatisticsEvent event) throws RemoteException {


		
		
		long activeTransactions = 0;
		Set<String> ips = event.getIps();
		
		int numResources = 0;
		PublishMeasurement pm;
		HashMap<String, PublishAttribute> statistics;
		long currentValue;
		for(String ip: ips){
			
			numResources = event.getNumResources(ResourceType.JMX, ip);
			
			for(int i = 0; i < numResources; i++){
				
				pm = event.getPublishMeasurement(ResourceType.JMX, i, ip);
				if(pm != null){
					statistics = pm.getValues();
					if(statistics != null){
						
						currentValue = (Long) statistics.get("LocalActiveTransactions").getValue();
						
						activeTransactions += currentValue;
					}
					
				}			
				
			}
			
		}
		
		
		
		OptimalPrevision op, previousOp = null;
		
		
		int minReplication = Integer.valueOf(Configuration.getConfiguration().getValue("minimalReplication")).intValue();
		int maxNodeNumber = Integer.valueOf(Configuration.getConfiguration().getValue("maximumNodeNumber")).intValue();
		op = nn.getOptimalPrevision(minReplication, maxNodeNumber, (int) activeTransactions);
		
		if(previousOp == null || !previousOp.equals(op)){
			previousOp = op;
			if(Configuration.getConfiguration().getValue("optimizationTarget").equals("Throughput")){
				System.out.println("Server = " + op.getServerThroughput() + " - " + "Replication = " + op.getReplicationThroughput());
				ac.setConfiguration((int) op.getServerThroughput(),(int) op.getReplicationThroughput());
		
			}else if(Configuration.getConfiguration().getValue("optimizationTarget").equals("ResponseTime")){
				System.out.println("Server = " + op.getServerResponseTime() + " - " + "Replication = " + op.getReplicationResponseTime());
				ac.setConfiguration((int) op.getServerResponseTime(), (int) op.getReplicationResponseTime());
			}else{
				System.out.println("Invalid value for OptimizationTarget parameter in configuration file");
			}
		}
		


	}

}