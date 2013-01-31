package simutools.infinispan.server;

import org.infinispan.Cache;
import org.infinispan.config.Configuration;
import org.infinispan.manager.DefaultCacheManager;

import javax.transaction.TransactionManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class Server {





    public static void main(String[] args){


        // read arguments
        if (args.length!=2) {
            System.out.println("Usage: java Server <host> <infinispan_config_file>");
            System.exit(-1);
        }
        int port = 2222;
        String address = args[0];
        String config = args[1];


        DefaultCacheManager cacheManager=null;
        Cache<Object, Object> cache = null;
        TransactionManager tm = null;

        Executor executor = null;


        ServerSocket socket = null;

        try {
            cacheManager = new DefaultCacheManager(config);
            // use a named cache, based on the 'default'
            //cacheManager.defineConfiguration("x", new Configuration());
            //cache = cacheManager.getCache("x");

            cache = cacheManager.getCache();
            tm=cache.getAdvancedCache().getTransactionManager();



            executor = Executors.newFixedThreadPool(64);


            socket = new ServerSocket(port, 0, InetAddress.getByName(address));



        } catch (IOException e) {
            System.err.println("Could not start server: " + e);
            System.exit(-1);
        }
        System.out.println("Infinispan Server accepting connections on port " + port);


        // request handler loop
        while (true) {
            Socket connection = null;
            try {
                // wait for request
                connection = socket.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }

            TransactionalService service = new TransactionalService(connection, tm, cache, cacheManager);

            executor.execute(service);
        }




    }
}
