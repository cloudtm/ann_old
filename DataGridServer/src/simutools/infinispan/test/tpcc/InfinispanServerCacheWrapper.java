package simutools.infinispan.test.tpcc;

import org.infinispan.Cache;
import org.radargun.CacheWrapper;
import org.radargun.utils.TypedProperties;

/**
 * Created by IntelliJ IDEA.
 * User: sebastiano
 * Date: 11/13/12
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfinispanServerCacheWrapper implements CacheWrapper {

    private Cache infinispanCache;

    public InfinispanServerCacheWrapper(Cache cache){
         this.infinispanCache = cache;
    }


    public void setUp(String s, boolean b, int i, TypedProperties typedProperties, String s1) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void tearDown() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void put(String s, Object o, Object o1) throws Exception {

        this.infinispanCache.put(o,o1);
    }

    public Object get(String s, Object o) throws Exception {
        return this.infinispanCache.get(o);
    }

    public void empty() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getNumMembers() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getReplicatedData(String s, String s1) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startTransaction() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void endTransaction(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CacheWrapper transactify() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean populateTpcc(int i, int i1, int i2, long l, long l1, long l2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
