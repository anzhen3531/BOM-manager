package ext.ziang.common.result;

/**
 * 返回结果工具类
 * @author wangzheng
 */
public class R {
    /**状态码*/
    private Integer code;
    /**请求是否成功*/
    private boolean success;
    /**返回消息*/
    private String msg;
    /**返回数据*/
    private Object data;

    public Integer getCode() {
        return code;
    }

    public R setCode(Integer code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public R setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public R setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 通过枚举创建一个对象
     * @param code
     */
    public R(Code code) {
        this.success = code.success;
        this.code = code.code;
        this.msg = code.msg;
    }

    /**
     * 通过返回状态码枚举和对象创建一个返回值对象
     * @param code
     * @param data
     */
    public R(Code code,Object data) {
        this.success = code.success;
        this.code = code.code;
        this.msg = code.msg;
        this.data = data;
    }

    /**
     * 自定义一个返回结果对象
     * @param code 状态码
     * @param success 成功或者失败
     * @param msg 消息
     * @param data 数据对象
     */
    public R(Integer code, boolean success, String msg, Object data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回默认的成功状态 <br/>
     * @return { code：10000，success：true, msg："操作成功！", data: null }
     */
    public static R ok(){
        return new R(Code.SUCCESS);
    }

    /**
     * 默认带数据的成功状态
     * @param data 数据
     * @return { code：10000，success：true, msg："操作成功！", data: 数据对象 }
     */
    public static R ok(Object data){
        return new R(Code.SUCCESS.code(),Code.SUCCESS.success(),Code.SUCCESS.message(),data);
    }

    /**
     * 返回自定义消息和数据
     * @param msg 返回消息
     * @param data 数据
     * @return { code：10000，success：true, msg：返回消息, data: 数据对象 }
     */
    public static R ok(String msg,Object data){
        return new R(Code.SUCCESS.code(),Code.SUCCESS.success(),msg,data);
    }

    /**
     * 返回一个自定义消息的成功状态
     * @param msg 消息信息
     * @return { code：10000，success：true, msg：返回消息}
     */
    public static R okMsg(String msg){
        return new R(Code.SUCCESS.code(),Code.SUCCESS.success(),msg,null);
    }

    /**
     * 返回默认的失败状态
     * @return { code：10001，success：true, msg："操作失败！", data: null }
     */
    public static R fail(){
        return new R(Code.FAIL);
    }

    /**
     * 默认带数据的失败状态
     * @param data 数据
     * @return { code：10001，success：false, msg："操作失败！", data: 数据对象 }
     */
    public static R fail(Object data){
        return new R(Code.FAIL.code(),Code.FAIL.success(),Code.FAIL.message(),data);
    }

    /**
     * 返回一个自定义消息和数据的失败状态
     * @param msg 消息
     * @param data 数据
     * @return { code：10001，success：false, msg：返回消息, data: 数据对象 }
     */
    public static R fail(String msg,Object data){
        return new R(Code.FAIL.code(),Code.FAIL.success,msg,data);
    }

    /**
     * 返回一个自定义消息的失败状态
     * @param msg 消息信息
     * @return { code：10001，success：false, msg：返回消息 }
     */
    public static R failMsg(String msg){
        return new R(Code.FAIL.code(),Code.FAIL.success(),msg,null);
    }

    /**
     * 返回一个没有消息的成功状态
     * @param data 数据对象
     * @return { code：10001，success：false, msg：null, data: 数据对象 }
     */
    public static R okData(Object data){
        return new R(Code.SUCCESS.code(),Code.SUCCESS.success(),null,data);
    }

    /**
     * 需要把后台异常信息输出到前台是使用。
     * @param exceptionMessage 异常信息
     * @return
     */
    public static R error(String exceptionMessage){
        return new R(Code.SERVER_ERROR.code(),Code.SERVER_ERROR.success,exceptionMessage,null);
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
