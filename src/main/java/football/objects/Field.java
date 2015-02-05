package football.objects;

import java.math.BigDecimal;

/**
 * Created by edwin_w on 1/21/15.
 *
 * Please get Field like the following way:
 *
 * Field = Field.Builder("ohohoh").build();
 */
public class Field {

    private String field_name;
    private String field_id;
    private String locate_city;
    private String location;
    private String phone_num;
    private String setup_time;
    private String open_time;
    private String close_time;
    private String field_desc;
    private BigDecimal field_score;
    private boolean night;
    private boolean indoor;

    private Field(Builder builder){
        this.field_id = builder.field_id;
        this.field_name = builder.field_name;
        this.field_desc = builder.field_desc;
        this.locate_city = builder.locate_city;
        this.location = builder.location;
        this.phone_num = builder.phone_num;
        this.setup_time = builder.setup_time;
        this.open_time = builder.open_time;
        this.close_time = builder.close_time;
        this.field_score = builder.field_score;
        this.night = builder.night;
        this.indoor = builder.indoor;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public String getLocate_city() {
        return locate_city;
    }

    public void setLocate_city(String locate_city) {
        this.locate_city = locate_city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getSetup_time() {
        return setup_time;
    }

    public void setSetup_time(String setup_time) {
        this.setup_time = setup_time;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getField_desc() {
        return field_desc;
    }

    public void setField_desc(String field_desc) {
        this.field_desc = field_desc;
    }

    public BigDecimal getField_score() {
        return field_score;
    }

    public void setField_score(BigDecimal field_score) {
        this.field_score = field_score;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public static class Builder{
        private String field_name;
        private String field_id;
        private String locate_city;
        private String location;
        private String phone_num;
        private String setup_time;
        private String open_time;
        private String close_time;
        private String field_desc;
        private BigDecimal field_score;
        private boolean night;
        private boolean indoor;

        public Builder(String field_name){
            this.field_name = field_name;
        }

        public Builder(String field_name, String field_id){
            this.field_name = field_name;
            this.field_id = field_id;
        }

        public Builder field_id(String field_id){
            this.field_id = field_id;
            return this;
        }

        public Builder locate_city(String locate_city){
            this.locate_city = locate_city;
            return this;
        }

        public Builder location(String location){
            this.location = location;
            return this;
        }

        public Builder phone_num(String phone_num){
            this.phone_num = phone_num;
            return this;
        }

        public Builder setup_time(String setup_time){
            this.setup_time = setup_time;
            return this;
        }

        public Builder open_time(String open_time){
            this.open_time = open_time;
            return this;
        }

        public Builder close_time(String close_time){
            this.close_time = close_time;
            return this;
        }

        public Builder field_desc(String field_desc){
            this.field_desc = field_desc;
            return this;
        }

        public Builder field_score(BigDecimal field_score){
            this.field_score = field_score;
            return this;
        }

        public Builder night(boolean night){
            this.night = night;
            return this;
        }

        public Builder indoor(boolean indoor){
            this.indoor = indoor;
            return this;
        }

        public Field build(){
            return new Field(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Field))
            return false;
        Field f = (Field)obj;
        return this == f || (this.field_id == null? f.field_id == null: this.field_id.equals(f.field_id) &&
                this.field_name == null? f.field_name == null: this.field_name.equals(f.field_name));
    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + (field_name == null? 0: field_name.hashCode());
        ret = 31 * ret + (field_id == null? 0: field_id.hashCode());
        return ret;
    }
}
