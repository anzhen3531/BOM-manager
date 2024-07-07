package ext.ziang.common.constants;

/**
 * 属性常量
 *
 * @author anzhen
 * @date 2024/04/20
 */
public enum AttributeConstants {

    CLASSIFY("Classify", "分类");

    private String innerName;
    private String displayName;

    AttributeConstants(String innerName, String displayName) {
        this.innerName = innerName;
        this.displayName = displayName;
    }

    public String getInnerName() {
        return this.innerName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
