package ann;

import java.io.*;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import eu.cloudtm.am.client.*;

// sebastiano

import eu.cloudtm.wpm.*;
import eu.cloudtm.wpm.connector.*;
import eu.cloudtm.wpm.logService.remote.events.SubscribeEvent;
import eu.cloudtm.wpm.logService.remote.listeners.WPMStatisticsRemoteListener;
import eu.cloudtm.wpm.logService.remote.listeners.WPMViewChangeRemoteListener;
import eu.cloudtm.wpm.logService.remote.observables.Handle;

public class StartDemo {
	
	private static int MINREPLICATION = 2;
	private static int MAXNODENUMBER = 4;
	
	private static double CLIENTNORM = 33;
	private static double SERVERNORM = 5;
	private static double REPLICATIONNORM = 5;
	private static double THROUGHPUTNORM = 2000;
	private static double RESPONSENORM = 17000;
	
	public static void main(String [] arg) throws RemoteException, UnknownHostException, NotBoundException{
		
		
		Properties props = new Properties();
		InputStream is = StartDemo.class.getClassLoader().getResourceAsStream("ANN.properties");
		if(is==null){
			is = StartDemo.class.getClass().getResourceAsStream("/ANN.properties");
		}
		try{
			props.load(is);
			if(is != null)
				is.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Configuration.getOrCreateConfiguration(props);
		
		
		// sebastiano 
		
		WPMConnector connector = new WPMConnector();
		
		
		double clientNorm;
		double serverNorm;
		double replicationNorm;
		double throughputNorm;
		double responseNorm;
		Nns nn;
		ActuatorClient ac;


		clientNorm = Double.valueOf(Configuration.getConfiguration().getValue("clientNormalization")).doubleValue();
		serverNorm = Double.valueOf(Configuration.getConfiguration().getValue("serverNormalization")).doubleValue();
		replicationNorm  = Double.valueOf(Configuration.getConfiguration().getValue("replicationNormalization")).doubleValue();
		throughputNorm  = Double.valueOf(Configuration.getConfiguration().getValue("throughputNormalization")).doubleValue();
		responseNorm  = Double.valueOf(Configuration.getConfiguration().getValue("responseNormalization")).doubleValue();

		nn = new Nns(clientNorm, serverNorm, replicationNorm, throughputNorm, responseNorm);

		nn.parseInputFile(Configuration.getConfiguration().getValue("trainingFilesPath")); // path dei file dei log per il training


		nn.trainNetworks();
		nn.loadNetworks();

		ac = new ActuatorClient(Configuration.getConfiguration().getValue("actuatorServer"), Integer.parseInt(Configuration.getConfiguration().getValue("actuatorServerPort")));

		
		
		WPMStatisticsRemoteListener statistics = new NNStatisticsRemoteListenerImpl(connector, nn, ac);
		
		String nodes = Configuration.getConfiguration().getValue("monitoredNodes");
		
		StringTokenizer strTok = new StringTokenizer(nodes, " ");
		int numNodes = strTok.countTokens();
		String[] subNodes = new String[numNodes]; 
		int i = 0;
		while(strTok.hasMoreTokens()){
			subNodes[i] = strTok.nextToken();
			
			i++;
		}
		
		Handle handle = connector.registerStatisticsRemoteListener(new SubscribeEvent(subNodes), statistics);
		
		WPMViewChangeRemoteListener viewListener = new NNViewChangeRemoteListenerImpl(connector, handle, nn, ac);
		
		connector.registerViewChangeRemoteListener(viewListener);
		
		
		
		/*
		double clientNorm = Double.valueOf(props.getProperty("clientNormalizzation")).doubleValue();
		double serverNorm = Double.valueOf(props.getProperty("serverNormalizzation")).doubleValue();
		double replicationNorm  = Double.valueOf(props.getProperty("replicationNormalizzation")).doubleValue();
		double throughputNorm  = Double.valueOf(props.getProperty("throughputNormalizzation")).doubleValue();
		double responseNorm  = Double.valueOf(props.getProperty("responseNormalizzation")).doubleValue();
		Nns nn = new Nns(clientNorm, serverNorm, replicationNorm, throughputNorm, responseNorm);
		nn.parseInputFile(props.getProperty("TrainingFilesPath")); // path dei file dei log per il training
		nn.trainNetworks();
		nn.loadNetworks();
		ActuatorClient ac = new ActuatorClient(props.getProperty("ActuatorServer"), Integer.parseInt(props.getProperty("Port")));
		
		File directory = new File(props.getProperty("MonitoringFilesPath"));
		
		
		File arc;
		String f = "";
		int clientNumber;
		OptimalPrevision op, previousOp = null;*/
		
		while(true){
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

/*			String lista[] = directory.list();
			for (String fileName : lista){
				if(!fileName.contains(".log.ack") && fileName.contains(".log")){
					f=fileName;
				}
			}
			// parsing di f con cui inizializzo clientNumber
			clientNumber = nn.getSampleFromInputFile(f);
			
			for (String fileName : lista){
				arc = new File(fileName);
				arc.delete();
			}
			
			int minReplication = Integer.valueOf(props.getProperty("minimalReplication")).intValue();
			int maxNodeNumber = Integer.valueOf(props.getProperty("maximumNodeNumber")).intValue();
			op = nn.getOptimalPrevision(minReplication, maxNodeNumber, clientNumber);
			
			if(previousOp == null || !previousOp.equals(op)){
				previousOp = op;
				if(props.getProperty("OptimizzationTarget").equals("Throughput")){
					System.out.println("Server = " + op.getServerThroughput() + " - " + "Replication = " + op.getReplicationThroughput());
					ac.setConfiguration((int) op.getServerThroughput(),(int) op.getReplicationThroughput());
				// setta la scelta ottimale sulla piattaforma con oggetto sebastiano
				}else if(props.getProperty("OptimizzationTarget").equals("ResponseTime")){
					System.out.println("Server = " + op.getServerResponseTime() + " - " + "Replication = " + op.getReplicationResponseTime());
					ac.setConfiguration((int) op.getServerResponseTime(), (int) op.getReplicationResponseTime());
				}else{
					System.out.println("Invalid value for OptimizzationTarget parameter in configuration file");
				}
			}
			try{
				Thread.sleep(4000);
			}catch (Exception e){
				e.printStackTrace();
			}*/
		}
	}
}
