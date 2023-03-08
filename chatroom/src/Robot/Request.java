package Robot;
/**
 * The Class Request.
 *
 * @author GUO
 */


public class Request {

    /** 属性: key. */
    private String key = "free";

    /** 属性: appid. */
    private String appid = "0";

    /** 属性: msg. */
    private String msg = "";


    /**
     * 默认构造方法
     */
    public Request(){}


    /**
     * 传参构造方法
     * @param msg
     */
    public Request(String msg){
        this.msg = msg;
    }

    /**
     * 获取对象中的key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置对象中的key
     * @param new key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取对象中的appid.
     * @return appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *  设置对象中的appid.
     * @param new appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 取得对象中的msg.
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置对象中的msg.
     * @param new msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}

