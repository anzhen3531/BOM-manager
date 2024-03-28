package ext.ziang.common.result;

/**
 * @author wangzheng
 */
public class Message {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    private String msg;
    private String type;

    public Message() {
    }

    public Message(String msg, String type) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public Message setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public static Message newMsg(){
        return new Message();
    }
    public static Message newMsg(String msg, String type){
        return new Message(msg,type);
    }

    public static Message newSuccess(String msg){
        return new Message(msg,SUCCESS);
    }
    public static Message newError(String msg){
        return new Message(msg,ERROR);
    }
    public static Message newWarning(String msg){
        return new Message(msg,WARNING);
    }


    @Override
    public String toString() {
        return "Message {" +
                "msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(Message.newError("123123"));
    }
}
