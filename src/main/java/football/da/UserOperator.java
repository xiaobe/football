package football.da;

import football.objects.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edwin_w on 1/27/15.
 *
 *
 * The user related record in the Redis in following format:
 *
 *   HMSET user:1000
 *      user_pwd <user_pwd>
 *      user_name <user_name>
 *      user_desc <user_desc>
 *      wechat_num ..
 *      phone_num ..
 *      qq_num
 *      free_time
 *      in_time
 *      group_id
 *      (city)
 *
 *   HSET users <user_name> <user_id>	// from user name to user id
 */
public class UserOperator extends RedisAccess {

    static final String USER = "user";
    static final String USERS = "users";

    static final String USER_ID = "user_id";
    static final String USER_NAME = "user_name";
    static final String USER_DESC = "user_desc";
    static final String WECHAT_NUM = "wechat_num";
    static final String PHONE_NUM = "phone_num";
    static final String QQ_NUM = "qq_num";
    static final String FREE_TIME = "free_time";
    static final String IN_TIME = "in_time";

    static final String GROUP_ID = "group_id";



    public enum Property{

        USER_NAME(UserOperator.USER_NAME),
        USER_DESC(UserOperator.USER_DESC),
        WECHAT_NUM(UserOperator.WECHAT_NUM),
        PHONE_NUM(UserOperator.PHONE_NUM),
        QQ_NUM(UserOperator.QQ_NUM),
        FREE_TIME(UserOperator.FREE_TIME),
        IN_TIME(UserOperator.IN_TIME),
        GROUP_ID(UserOperator.GROUP_ID);


        private String property;

        Property(String property){
            this.property = property;
        }

        private String getProerty(){
            return property;
        }
    }


    private String user_id;
    private final String USER_TABLE;

    private UserOperator(Jedis jedis, String user_id){
        this.jedis = jedis;
        this.user_id = user_id;
        USER_TABLE = getSpecified(user_id);
    }

    public static UserOperator getInstanceFromUserId(Jedis jedis, String user_id){
        return new UserOperator(jedis, user_id);
    }

    public static UserOperator getInstanceFromUserName(Jedis jedis, String user_name){
        String user_id = getUserID(jedis, user_name);
        return getInstanceFromUserId(jedis, user_id);
    }

    public static String getUserID(Jedis jedis, String user_name){
        return jedis.hget(USERS, user_name);
    }

    public static void addUser(Jedis jedis, final User u){
        Map<String, String> keys = new HashMap<String, String>(){{
            put(USER_NAME, u.getUser_name());
            put(USER_DESC, u.getUser_desc());
            put(WECHAT_NUM, u.getWechat_num());
            put(PHONE_NUM, u.getPhone_num());
            put(QQ_NUM, u.getQq_num());
            put(FREE_TIME, u.getFree_time());
            put(IN_TIME, u.getIn_time());
            put(GROUP_ID, u.getGroup_id());
        }};

        String specific_user = getSpecified(u.getUser_id());

        jedis.watch(specific_user, USERS);

        // begin the transaction
        Transaction trans = jedis.multi();

        // record the new user
        trans.hmset(specific_user, keys);

        // record for retrieving user_id from user_name
        // HSET users <user_name> <user_id>
        trans.hset(USERS, u.getUser_name(), u.getUser_id());

        // submit the transaction
        trans.exec();
    }

    public String getProperty(Property property){
        return jedis.hget(USER_TABLE, property.getProerty());
    }

    public void setProperty(Property property, String value){
        jedis.hset(USER_TABLE, property.getProerty(), value);
    }

    public User getUser(){
        List<String> u = jedis.hmget(USER_TABLE,
                USER_NAME,
                USER_DESC,
                WECHAT_NUM,
                PHONE_NUM,
                QQ_NUM,
                FREE_TIME,
                IN_TIME,
                GROUP_ID);

        return new User.Builder(user_id,
                u.get(0)).
                user_desc(u.get(1)).
                wechat_num(u.get(2)).
                phone_num(u.get(3)).
                qq_num(u.get(4)).
                free_time(u.get(5)).
                in_time(u.get(6)).
                group_id(u.get(7)).
                build();
    }

    private static String getSpecified(String user_id){
        return USER + ":" + user_id;
    }


//    public String getUserName(){
//        return jedis.hget(USER_TABLE, USER_NAME);
//    }
//
//    public String getUserDesc(){
//        return jedis.hget(USER_TABLE, USER_DESC);
//    }
//
//    public String getWechatNum(){
//        return jedis.hget(USER_TABLE, WECHAT_NUM);
//    }
//
//    public String getPhoneNum(){
//        return jedis.hget(USER_TABLE, PHONE_NUM);
//    }
//
//    public String getQqNum(){
//        return jedis.hget(USER_TABLE, QQ_NUM);
//    }
//
//    public String getFreeTime(){
//        return jedis.hget(USER_TABLE, FREE_TIME);
//    }
//
//    public String getInTime(){
//        return jedis.hget(USER_TABLE, IN_TIME);
//    }
//
//    public String getGroupId(){
//        return jedis.hget(USER_TABLE, GROUP_ID);
//    }



//    public void setUserName(String userName){
//        jedis.hset(USER_TABLE, USER_NAME, userName);
//    }
//
//    public void setUserDesc(String userDesc){
//        jedis.hset(USER_TABLE, USER_DESC, userDesc);
//    }
//
//    public void setWechatNum(String wechatNum){
//        jedis.hset(USER_TABLE, WECHAT_NUM, wechatNum);
//    }
//
//    public void setPhoneNum(String phoneNum){
//        jedis.hset(USER_TABLE, PHONE_NUM, phoneNum);
//    }
//
//    public void setQqNum(String qqNum){
//        jedis.hset(USER_TABLE, QQ_NUM, qqNum);
//    }
//
//    public void setFreeTime(String freeTime){
//        jedis.hset(USER_TABLE, FREE_TIME, freeTime);
//    }
//
//    public void setInTime(String inTime){
//        jedis.hset(USER_TABLE, IN_TIME, inTime);
//    }
//
//    public void setGroupId(String groupId){
//        jedis.hset(USER_TABLE, GROUP_ID, groupId);
//    }


}
