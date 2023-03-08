package Robot;
/**
 * The Class Request.
 *
 * @author GUO
 */


public class Request {

    /** ����: key. */
    private String key = "free";

    /** ����: appid. */
    private String appid = "0";

    /** ����: msg. */
    private String msg = "";


    /**
     * Ĭ�Ϲ��췽��
     */
    public Request(){}


    /**
     * ���ι��췽��
     * @param msg
     */
    public Request(String msg){
        this.msg = msg;
    }

    /**
     * ��ȡ�����е�key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * ���ö����е�key
     * @param new key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * ��ȡ�����е�appid.
     * @return appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *  ���ö����е�appid.
     * @param new appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * ȡ�ö����е�msg.
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * ���ö����е�msg.
     * @param new msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}

