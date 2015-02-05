package da;

import football.objects.Group;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edwin_w on 12/28/14.
 *
 *  The group related record in the Redis in following format:
 *
 *  HMSET group:1000
 *      master_id <master_id>
 *      master_name <master_name>
 *      ceiling <ceiling>
 *      group_name <group_name>
 *      group_pwd <group_pwd>
 *      group_desc ..
 *      setup_time ..
 *      auth .. 		// true or false
 *      (city)
 *
 * HSET groups <group_name> <group_id>
 */
public class GroupOperator extends RedisAccess{

    static final String GROUP = "group";
    static final String GROUPS = "groups";

    static final String GROUP_ID = "group_id";
    static final String GROUP_NAME = "group_name";
    static final String GROUP_PWD = "group_pwd";
    static final String GROUP_DESC = "group_desc";
    static final String MASTER_ID = "master_id";
    static final String MASTER_NAME = "master_name";
    static final String SETUP_TIME = "setup_time";
    static final String AUTH = "auth";
    static final String CEILING = "ceiling";


    public enum Property{

        GROUP_NAME(GroupOperator.GROUP_NAME),
        GROUP_PWD(GroupOperator.GROUP_PWD),
        GROUP_DESC(GroupOperator.GROUP_DESC),
        MASTER_ID(GroupOperator.MASTER_ID),
        MASTER_NAME(GroupOperator.MASTER_NAME),
        SETUP_TIME(GroupOperator.SETUP_TIME),
        AUTH(GroupOperator.AUTH),
        CEILING(GroupOperator.CEILING);


        private String property;

        Property(String property){
            this.property = property;
        }

        private String getProperty(){
            return property;
        }

    }


    private String group_id;
    private final String GROUP_TABLE;

    private GroupOperator(Jedis jedis, String group_id){
        this.jedis = jedis;
        this.group_id = group_id;

        GROUP_TABLE = getSpecified(group_id);
    }

    public static GroupOperator getInstanceFromGroupId(Jedis jedis, String group_id){
        return new GroupOperator(jedis, group_id);
    }

    public static GroupOperator getInstanceFromGroupName(Jedis jedis, String group_name){
        String group_id = getGroupId(jedis, group_name);
        return getInstanceFromGroupId(jedis, group_id);
    }

    public static String getGroupId(Jedis jedis, String group_name){
        return jedis.hget(GROUPS, group_name);
    }

    public static void addGroup(Jedis jedis, final Group g){
        Map<String, String> keys = new HashMap<String, String>(){{
            put(GROUP_NAME, g.getGroup_name());
            put(GROUP_PWD, g.getGroup_pwd());
            put(GROUP_DESC, g.getGroup_desc());
            put(MASTER_ID, g.getMaster_id());
            put(MASTER_NAME, g.getMaster_name());
            put(SETUP_TIME, g.getSetup_time());
            put(AUTH, g.getAuth()? "1": "0");
            put(CEILING, String.valueOf(g.getCeiling()));
        }};

        String specific_group = getSpecified(g.getGroup_id());

        jedis.watch(specific_group, GROUPS);

        // begin the transaction
        Transaction trans = jedis.multi();

        //record the new group
        trans.hmset(specific_group, keys);
        // record for retrieving group_id from group_name
        // HSET groups <group_name> <group_id>
        trans.hset(GROUPS, g.getGroup_name(), g.getGroup_id());

        // exec and end trans
        trans.exec();

    }

    public String getProperty(Property property){
        return jedis.hget(GROUP_TABLE, property.getProperty());
    }

    public void setProperty(Property property, String value){
        jedis.hset(GROUP_TABLE, property.getProperty(), value);
    }

    public Group getGroup(){
        List<String> g = jedis.hmget(GROUP_TABLE,
                GROUP_NAME,
                GROUP_PWD,
                GROUP_DESC,
                MASTER_ID,
                MASTER_NAME,
                SETUP_TIME,
                AUTH,
                CEILING);

        return new Group.Builder(group_id,
                g.get(0)).
                group_pwd(g.get(1)).
                group_desc(g.get(2)).
                master_id(g.get(3)).
                master_name(g.get(4)).
                setup_time(g.get(5)).
                auth(!(g.get(6).equals("0"))).
                ceiling(Integer.getInteger(g.get(7))).
                build();
    }


    // the records for user is "group:<group_id>"
    private static String getSpecified(String group_id){
        return GROUP + ":" + group_id;
    }


//    public String getGroupName(){
//        return jedis.hget(GROUP_TABLE, GROUP_NAME);
//    }
//
//    public String getGroupPwd(){
//        return jedis.hget(GROUP_TABLE, GROUP_PWD);
//    }
//
//    public String getGroupDesc(){
//        return jedis.hget(GROUP_TABLE, GROUP_DESC);
//    }
//
//    public String getMasterId(){
//        return jedis.hget(GROUP_TABLE, MASTER_ID);
//    }
//
//    public String getMasterName(){
//        return jedis.hget(GROUP_TABLE, MASTER_NAME);
//    }
//
//    public String getSetupTime(){
//        return jedis.hget(GROUP_TABLE, SETUP_TIME);
//    }
//
//    public String getAuth(){
//        return jedis.hget(GROUP_TABLE, AUTH);
//    }
//
//    public String getCeiling(){
//        return jedis.hget(GROUP_TABLE, CEILING);
//    }



//
//    public void setGroupName(String group_name){
//        jedis.hset(GROUP_TABLE, GROUP_NAME, group_name);
//    }
//
//    public void setGroupPwd(String group_pwd){
//        jedis.hset(GROUP_TABLE, GROUP_PWD, group_pwd);
//    }
//
//    public void setGroupDesc(String group_desc){
//        jedis.hset(GROUP_TABLE, GROUP_DESC, group_desc);
//    }
//
//    public void setMasterId(String master_id){
//        jedis.hset(GROUP_TABLE, MASTER_ID, master_id);
//    }
//
//    public void setMasterName(String master_name){
//        jedis.hset(GROUP_TABLE, MASTER_NAME, master_name);
//    }
//
//    public void setSetupTime(String setup_time){
//        jedis.hset(GROUP_TABLE, SETUP_TIME, setup_time);
//    }
//
//    public void setAuth(String auth){
//        jedis.hset(GROUP_TABLE, AUTH, auth);
//    }
//
//    public void setCeiling(String ceiling){
//        jedis.hset(GROUP_TABLE, CEILING, ceiling);
//    }



}
