package football.objects;

/**
 * Created by edwin_w on 12/27/14.
 *
 * Please get User like the following way:
 *
 * User = User.Builder("123", "edwin").build();
 */
public class User {

    private User(Builder builder){
        user_id = builder.user_id;
        user_name = builder.user_name;
        user_desc = builder.user_desc;
        wechat_num = builder.wechat_num;
        phone_num = builder.phone_num;
        qq_num = builder.qq_num;
        free_time = builder.free_time;
        in_time = builder.in_time;
        group_id = builder.group_id;
    }

    private String user_id;
    private String user_name;
    private String user_desc;
    private String wechat_num;
    private String phone_num;
    private String qq_num;
    private String free_time;
    private String in_time;
    private String group_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getWechat_num() {
        return wechat_num;
    }

    public void setWechat_num(String wechat_num) {
        this.wechat_num = wechat_num;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getQq_num() {
        return qq_num;
    }

    public void setQq_num(String qq_num) {
        this.qq_num = qq_num;
    }

    public String getFree_time() {
        return free_time;
    }

    public void setFree_time(String free_time) {
        this.free_time = free_time;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getGroup_id(){
        return group_id;
    }

    public void setGroup_id(String group_id){
        this.group_id = group_id;
    }

    public static class Builder{
        private String user_id;
        private String user_name;
        private String user_desc;
        private String wechat_num;
        private String phone_num;
        private String qq_num;
        private String free_time;
        private String in_time;
        private String group_id;

        public Builder(String user_id, String user_name){
            this.user_id = user_id;
            this.user_name = user_name;
        }

        public Builder user_desc(String user_desc){
            this.user_desc = user_desc;
            return this;
        }

        public Builder wechat_num(String wechat_num){
            this.wechat_num = wechat_num;
            return this;
        }

        public Builder phone_num(String phone_num){
            this.phone_num = phone_num;
            return this;
        }

        public Builder qq_num(String qq_num){
            this.qq_num = qq_num;
            return this;
        }

        public Builder free_time(String free_time){
            this.free_time = free_time;
            return this;
        }

        public Builder in_time(String in_time){
            this.in_time = in_time;
            return this;
        }

        public Builder group_id(String group_id){
            this.group_id = group_id;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }



    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User))
            return false;

        User o = (User)obj;
        return this == o ||
                (user_id == null? o.user_id == null: user_id.equals(o.user_id) &&
                        user_name == null? o.user_name == null : user_name.equals(o.user_name));

    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + (user_id == null? 0: user_id.hashCode());
        ret = 31 * ret + (user_name == null? 0: user_name.hashCode());
        return ret;
    }

    /**
     * Return a brief information of the user object. The exact details of the representation are
     * unspecified and subject to change, but the following may be regarded as typical:
     *
     * "[User: user_id=123, user_name=edwin, user_desc=cool man]"
     */
    @Override
    public String toString() {
        return String.format("[User: user_id=%s, user_name=%s, user_desc=%s]", user_id, user_name, user_desc);
    }
}
