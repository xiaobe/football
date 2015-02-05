package da;

import redis.clients.jedis.Jedis;
import util.TimeUtil;

import java.util.Map;

/**
 * Created by edwin_w on 12/28/14.
 */
public class GroupMemberOperator extends RedisAccess{

    static final String GROUP_MEMBER = "group_member";

    public GroupMemberOperator(Jedis jedis){
        this.jedis = jedis;
    }

    public void addMember(String group_id, String user_id){
        addMember(group_id, user_id, TimeUtil.getCurrentTime());
    }

    public void addMember(String group_id, String user_id, String join_time){
        jedis.zadd(getSpecified(group_id), Double.valueOf(join_time), user_id);
    }

    // comment this method because we should not use the current time as the join time for all the new members but use the ones from input
//    public void addMembers(String group_id, List<String> keys){
//        String group_member = getSpecified(group_id);
//        for(String user_id: keys){
//            jedis.zadd(group_member, Double.valueOf(TimeUtil.getCurrentTime()), user_id);
//        }
//    }

    private String getSpecified(String group_id){
        return GROUP_MEMBER + ":" + group_id;
    }
}
