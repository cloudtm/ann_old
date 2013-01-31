package ann;

import java.util.Properties;

public final class Configuration {
	
	private Properties props;
	
	private static Configuration instance;
	
	private Configuration(Properties props){
		
		this.props = props;
		
	}
	
	public static synchronized Configuration getOrCreateConfiguration(Properties props){
		
		if(Configuration.instance == null){
			
			Configuration.instance = new Configuration(props);
			
		}
		
		return Configuration.instance;
		
		
	} 
	
	public static Configuration getConfiguration(){
		
		return instance;
		
	}
	
	
	public String getValue(String propertyName){
		
		return this.props.getProperty(propertyName);
		
	}
	
	

}
