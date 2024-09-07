package ext.ziang.common.constants;

/**
 * 基本枚举
 */
public enum StateEnum {
    START(0, "开启"),
    STOP(1, "关闭"),
    DELETE (-1, "删除")

    ;
    private final Integer value;
    private final String name;

    StateEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
