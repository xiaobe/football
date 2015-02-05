package da;

import football.objects.Field;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edwin_w on 1/21/15.
 *
 * The field related record in the Redis in following format:
 *
 * HMSET field:5
 *  field_name <field_name>
 *  locate_city <locate_city>
 *  location <location>
 *  phone_num ..
 *  night ..
 *  indoor ..
 *  setup_time ..
 *  open_time ..
 *  close_time ..
 *  field_desc ..
 *  field_sorce ..
 *
 *  HSET fileds <field_name> <field_id>
 *
 */
public class FieldOperator extends RedisAccess {

    static final String FIELD = "field";
    static final String FIELDS = "fields";

    static final String FIELD_NAME = "field_name";
    static final String FIELD_ID = "field_id";
    static final String LOCATE_CITY = "locate_city";
    static final String LOCATION = "location";
    static final String PHONE_NUM = "phone_num";
    static final String NIGHT = "night";
    static final String INDOOR = "indoor";
    static final String SETUP_TIME = "setup_time";
    static final String OPEN_TIME = "open_time";
    static final String CLOSE_TIME = "close_time";
    static final String FIELD_DESC = "field_desc";
    static final String FIELD_SCORE = "field_score";


    public enum Property{

        FIELD_NAME(FieldOperator.FIELD_NAME),
        LOCATE_CITY(FieldOperator.LOCATE_CITY),
        LOCATION(FieldOperator.LOCATION),
        PHONE_NUM(FieldOperator.PHONE_NUM),
        NIGHT(FieldOperator.NIGHT),
        INDOOR(FieldOperator.INDOOR),
        SETUP_TIME(FieldOperator.SETUP_TIME),
        OPEN_TIME(FieldOperator.OPEN_TIME),
        CLOSE_TIME(FieldOperator.CLOSE_TIME),
        FIELD_DESC(FieldOperator.FIELD_DESC),
        FIELD_SCORE(FieldOperator.FIELD_SCORE);

        private String property;
        Property(String property){
            this.property = property;
        }

        private String getProperty(){
            return property;
        }
    }


    private String field_id;
    private final String FIELD_TABLE;

    private FieldOperator(Jedis jedis, String field_id){
        this.jedis = jedis;
        this.field_id = field_id;
        FIELD_TABLE = getSpecified(field_id);
    }

    public FieldOperator getInstanceFromFieldId(Jedis jedis, String field_id){
        return new FieldOperator(jedis, field_id);
    }

    public FieldOperator getInstanceFromFieldName(Jedis jedis, String field_name){
        String field_id = getFieldId(jedis, field_name);
        return getInstanceFromFieldId(jedis, field_id);
    }

    public static void addField(Jedis jedis, final Field field){
        Map<String, String> keys = new HashMap<String, String>(){{
            put(FIELD_NAME, field.getField_name());
            put(LOCATE_CITY, field.getLocate_city());
            put(LOCATION, field.getLocation());
            put(PHONE_NUM, field.getPhone_num());
            put(NIGHT, field.isNight()? "1": "0");
            put(INDOOR, field.isIndoor()? "1": "0");
            put(SETUP_TIME, field.getSetup_time());
            put(OPEN_TIME, field.getOpen_time());
            put(CLOSE_TIME, field.getClose_time());
            put(FIELD_DESC, field.getField_desc());
            put(FIELD_SCORE, field.getField_score().toString());
        }};

        String specific_field = getSpecified(field.getField_id());

        jedis.watch(specific_field, FIELDS);

        // begin the transaction
        Transaction trans = jedis.multi();

        //record the new group
        trans.hmset(specific_field, keys);
        // record for retrieving group_id from group_name
        // HSET groups <group_name> <group_id>
        trans.hset(FIELDS, field.getField_name(), field.getField_id());

        // exec and end trans
        trans.exec();

    }

    public static String getFieldId(Jedis jedis, String field_name){
        return jedis.hget(FIELDS, field_name);
    }

    public Field getField(){
        List<String> f = jedis.hmget(FIELD_TABLE,
                FIELD_NAME,
                LOCATE_CITY,
                LOCATION,
                PHONE_NUM,
                SETUP_TIME,
                OPEN_TIME,
                CLOSE_TIME,
                FIELD_DESC,
                FIELD_SCORE,
                NIGHT,
                INDOOR);

        return new Field.Builder(f.get(0), field_id).
                locate_city(f.get(1)).
                location(f.get(2)).
                phone_num(f.get(3)).
                setup_time(f.get(4)).
                open_time(f.get(5)).
                close_time(f.get(6)).
                field_desc(f.get(7)).
                field_score(new BigDecimal(f.get(8))).
                night(!f.get(9).equals("0")).
                indoor(!f.get(10).equals("0")).build();
    }

    public String getProperty(Property property){
        return jedis.hget(FIELD_TABLE, property.getProperty());
    }

    public void setProperty(Property property, String value){
        jedis.hset(FIELD_TABLE, property.getProperty(), value);
    }

    // the records for user is "field:<field_id>"
    private static String getSpecified(String field_id){
        return FIELD + ":" + field_id;
    }


//    public String getFieldName(){
//        return jedis.hget(FIELD_TABLE, FIELD_NAME);
//    }
//
//    public String getLocateCity(){
//        return jedis.hget(FIELD_TABLE, LOCATE_CITY);
//    }
//
//    public String getLocation(){
//        return jedis.hget(FIELD_TABLE, LOCATION);
//    }
//
//    public String getPhoneNum(){
//        return jedis.hget(FIELD_TABLE, PHONE_NUM);
//    }
//
//    public String getNight(){
//        return jedis.hget(FIELD_TABLE, NIGHT);
//    }
//
//    public String getIndoor(){
//        return jedis.hget(FIELD_TABLE, INDOOR);
//    }
//
//    public String getSetupTime(){
//        return jedis.hget(FIELD_TABLE, SETUP_TIME);
//    }
//
//    public String getOpenTime(){
//        return jedis.hget(FIELD_TABLE, OPEN_TIME);
//    }
//
//    public String getCloseTime(){
//        return jedis.hget(FIELD_TABLE, CLOSE_TIME);
//    }
//
//    public String getFieldDesc(){
//        return jedis.hget(FIELD_TABLE, FIELD_DESC);
//    }
//
//    public String getFieldScore(){
//        return jedis.hget(FIELD_TABLE, FIELD_SCORE);
//    }



//    public void setFieldName(String file_name){
//        jedis.hset(FIELD_TABLE, FIELD_NAME, file_name);
//    }
//
//    public void setLocateCity(String locate_city){
//        jedis.hset(FIELD_TABLE, LOCATE_CITY, locate_city);
//    }
//
//    public void setLocation(String location){
//        jedis.hset(FIELD_TABLE, LOCATION, location);
//    }
//
//    public void setPhoneNum(String phone_num){
//        jedis.hset(FIELD_TABLE, PHONE_NUM, phone_num);
//    }
//
//    public void setSetupTime(String setup_time){
//        jedis.hset(FIELD_TABLE, SETUP_TIME, setup_time);
//    }
//
//    public void setOpenTime(String open_time){
//        jedis.hset(FIELD_TABLE, OPEN_TIME, open_time);
//    }
//
//    public void setCloseTime(String close_time){
//        jedis.hset(FIELD_TABLE, CLOSE_TIME, close_time);
//    }
//
//    public void setFieldDesc(String field_desc){
//        jedis.hset(FIELD_TABLE, FIELD_DESC, field_desc);
//    }
//
//    public void setFieldScore(String field_score){
//        jedis.hset(FIELD_TABLE, FIELD_SCORE, field_score);
//    }
//
//    public void setNight(String night){
//        jedis.hset(FIELD_TABLE, NIGHT, night);
//    }
//
//    public void setIndoor(String indoor){
//        jedis.hset(FIELD_TABLE, INDOOR, indoor);
//    }





}
