package ann;

import java.rmi.RemoteException;

import eu.cloudtm.am.client.ActuatorClient;
import eu.cloudtm.wpm.connector.WPMConnector;
import eu.cloudtm.wpm.logService.remote.events.PublishViewChangeEvent;
import eu.cloudtm.wpm.logService.remote.events.SubscribeEvent;
import eu.cloudtm.wpm.logService.remote.listeners.WPMStatisticsRemoteListener;
import eu.cloudtm.wpm.logService.remote.listeners.WPMViewChangeRemoteListener;
import eu.cloudtm.wpm.logService.remote.observables.Handle;

public class NNViewChangeRemoteListenerImpl implements WPMViewChangeRemoteListener {

	private WPMConnector connector;
	
	private Handle prevStatisticsListener;
	
	private Nns nn;
	private ActuatorClient ac;
	
	public NNViewChangeRemoteListenerImpl(WPMConnector connector, Handle prevStatisticsListener, Nns nn, ActuatorClient ac){
		
		this.connector = connector;
		
		this.prevStatisticsListener = prevStatisticsListener;
		
		this.nn= nn;
		this.ac = ac;
	}
	
	@Override
	public void onViewChange(PublishViewChangeEvent event) throws RemoteException {
		
		
		if(this.prevStatisticsListener != null){
			this.connector.removeStatisticsRemoteListener(this.prevStatisticsListener);
			this.prevStatisticsListener = null;
		}
		
		
		String[] newNodes = event.getCurrentVMs();
		
		WPMStatisticsRemoteListener statistics = new NNStatisticsRemoteListenerImpl(connector, this.nn, this.ac);
		
		this.prevStatisticsListener = connector.registerStatisticsRemoteListener(new SubscribeEvent(newNodes), statistics);

	}

}
