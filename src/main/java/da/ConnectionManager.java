package da;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by edwin_w on 12/27/14.
 */
public class ConnectionManager {

    static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public static Jedis  getConnection(){
        return pool.getResource();
    }

    public static void closeConnection(){
        pool.destroy();
    }
}