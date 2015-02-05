package football.objects;

/**
 * Created by edwin_w on 12/28/14.
 *
 * Please get Group like the following way:
 *
 * Group = Group.Builder("321", "gogogo").build();
 */
public class Group {

    private String group_id;
    private String master_id;
    private String master_name;
    private int ceiling;
    private String group_name;
    private String group_pwd;
    private String group_desc;
    private String setup_time;
    private boolean auth;

    private Group(Builder builder){
        group_id = builder.group_id;
        master_id = builder.master_id;
        master_name = builder.master_name;
        ceiling = builder.ceiling;
        group_name = builder.group_name;
        group_pwd = builder.group_pwd;
        group_desc = builder.group_desc;
        setup_time = builder.setup_time;
        auth = builder.auth;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getMaster_name(){
        return master_name;
    }

    public void setMaster_name(String master_name){
        this.master_name = master_name;
    }

    public int getCeiling() {
        return ceiling;
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public String getGroup_name(){
        return group_name;
    }

    public void setGroup_name(String group_name){
        this.group_name = group_name;
    }

    public String getGroup_pwd() {
        return group_pwd;
    }

    public void setGroup_pwd(String group_pwd) {
        this.group_pwd = group_pwd;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public String getSetup_time() {
        return setup_time;
    }

    public void setSetup_time(String setup_time) {
        this.setup_time = setup_time;
    }

    public boolean getAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public static class Builder{
        private String group_id;
        private String master_id;
        private String master_name;
        private int ceiling;
        private String group_name;
        private String group_pwd;
        private String group_desc;
        private String setup_time;
        private boolean auth;

        public Builder(String group_id, String group_name){
            this.group_id = group_id;
            this.group_name = group_name;
        }

        public Builder master_id(String master_id){
            this.master_id = master_id;
            return this;
        }

        public Builder master_name(String master_name){
            this.master_name = master_name;
            return this;
        }

        public Builder ceiling(int ceiling){
            this.ceiling = ceiling;
            return this;
        }

        public Builder group_pwd(String group_pwd){
            this.group_pwd = group_pwd;
            return this;
        }

        public Builder group_desc(String group_desc){
            this.group_desc = group_desc;
            return this;
        }

        public Builder setup_time(String setup_time){
            this.setup_time = setup_time;
            return this;
        }

        public Builder auth(boolean auth){
            this.auth = auth;
            return this;
        }

        public Group build(){
            return new Group(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  Group)){
            return false;
        }
        Group o = (Group)obj;
        return this == o ||
                (group_id == null? o.group_id == null: group_id.equals(o.group_id) &&
                        group_name == null? o.group_name == null: group_name.equals(o.group_name));
    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + (group_id == null? 0: group_id.hashCode());
        ret = 31 * ret + (group_name == null? 0: group_name.hashCode());
        return ret;
    }

    /**
     * Return a brief information of the group object. The exact details of the representation are
     * unspecified and subject to change, but the following may be regarded as typical:
     *
     * "[Group: group_id=123, group_name=gogogo, master_name=edwin, group_desc=cool group]"
     */
    @Override
    public String toString() {
        return String.format("Group: group_id=%s, group_name=%s, master_name=%s, group_desc=%s", group_id, group_name, master_name, group_desc);
    }
}
