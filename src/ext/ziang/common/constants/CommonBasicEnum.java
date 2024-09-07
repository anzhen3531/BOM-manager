package ext.ziang.common.constants;

public enum CommonBasicEnum {
    START(0, "开启"),
    STOP(1, "关闭");
    private Integer value;
    private String name;

    CommonBasicEnum(Integer value, String name) {
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
