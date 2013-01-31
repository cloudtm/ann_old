package eu.cloudtm.am.server;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/*
* @Author Sebastiano Peluso
 */
public final class ActuatorServer {

    private static String ipAddress = "localhost";
    private static int port = 8888;
    private static String infinispanServerDirectory = null;

    private static String infinispanNodes = null;
    private static String userInfinispanNodes = null;

    private static int infinispanJmxPort = -1;
    private static String infinispanJmxDomain = null;
    private static String infinispanJmxCacheName = null;
    private static String infinispanJmxReplicationType = null;
    private static String infinispanJmxCacheManager = null;


    public static void main(String[] args){


        try {

            Properties props = new Properties();

            InputStream is = ActuatorServer.class.getClassLoader().getResourceAsStream("actuator-server.properties");
            if(is==null){
                is = ActuatorServer.class.getClass().getResourceAsStream("/actuator-server.properties");
            }
            props.load(is);


            if(is != null)
                is.close();



            if(props.getProperty("ip") != null){
                ipAddress = props.getProperty("ip");
            }

            if(props.getProperty("port") != null){
                port = Integer.parseInt(props.getProperty("port"));
            }

            if(props.getProperty("infinispanServerDirectory") != null){
                infinispanServerDirectory = props.getProperty("infinispanServerDirectory");

            }



            if(props.getProperty("infinispanNodes") != null){
                infinispanNodes = props.getProperty("infinispanNodes");
            }

            if(props.getProperty("userInfinispanNodes") != null){
                userInfinispanNodes = props.getProperty("userInfinispanNodes");
            }

            if(props.getProperty("infinispanJmxPort") != null){
                infinispanJmxPort = Integer.parseInt(props.getProperty("infinispanJmxPort"));
            }


            if(props.getProperty("infinispanJmxDomain") != null){
                infinispanJmxDomain = props.getProperty("infinispanJmxDomain");
            }

            if(props.getProperty("infinispanJmxCacheName") != null){
                infinispanJmxCacheName = props.getProperty("infinispanJmxCacheName");
            }

            if(props.getProperty("infinispanJmxReplicationType") != null){
                infinispanJmxReplicationType = props.getProperty("infinispanJmxReplicationType");
            }

            if(props.getProperty("infinispanJmxReplicationType") != null){
                infinispanJmxCacheManager = props.getProperty("infinispanJmxCacheManager");
            }



            ServerSocket socket = new ServerSocket(port, 0, InetAddress.getByName(ipAddress));

            System.out.println("Infinispan Servers on nodes "+props.getProperty("infinispanNodes"));

            System.out.println();
            System.out.println("Autonomic Manager - Actuator Server accepting connections on " + ipAddress+":"+port);



            Executor executor = Executors.newFixedThreadPool(4);


            // request handler loop
            while (true) {
                Socket connection = null;
                try{
                    // wait for request
                    connection = socket.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ActuatorService service = new ActuatorService(connection, infinispanServerDirectory, infinispanNodes, userInfinispanNodes, infinispanJmxPort, infinispanJmxDomain, infinispanJmxCacheName, infinispanJmxReplicationType, infinispanJmxCacheManager);

                executor.execute(service);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
