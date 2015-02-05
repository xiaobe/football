import football.da.UserOperator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.PrintStream;

/**
 * Created by edwin_w on 12/25/14.
 */
public class CheckJredis {
    public static void main(String strs[])
    {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

        try(Jedis jedis = pool.getResource())
        {

            PrintStream output = System.out;

            // give the user id and initialize the user operator
            UserOperator uo = UserOperator.getInstanceFromUserId(jedis, "123");

            // the username before setting
            String user_name = uo.getProperty(UserOperator.Property.USER_NAME);
            output.println("Before setting: the username of 123 is " + user_name);


            // the username after setting
            uo.setProperty(UserOperator.Property.USER_NAME, "bigbig");
            user_name = uo.getProperty(UserOperator.Property.USER_NAME);
            output.println("After setting: the username of 123 is " + user_name);

        }


        pool.destroy();
    }
}
