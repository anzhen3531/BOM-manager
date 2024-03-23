package ext.ziang.histrory.entity;

import java.util.HashMap;

/**
 * 导入历史记录 wtpart bean
 *
 * @author anzhen
 * @date 2024/03/20
 */
public class ImportHistoryWTPartBean {
    String number;
    String name;
    String classify;
    String version;
    String unit;
    String description;
    String oidERPNumber;
    String container;
    String locationPath;
    String type;
    String partType;
    String lifeCycleState;
    HashMap<String, String> ibaMapping;

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOidERPNumber() {
        return oidERPNumber;
    }

    public void setOidERPNumber(String oidERPNumber) {
        this.oidERPNumber = oidERPNumber;
    }

    public HashMap<String, String> getIbaMapping() {
        return ibaMapping;
    }

    public void setIbaMapping(HashMap<String, String> ibaMapping) {
        this.ibaMapping = ibaMapping;
    }

    @Override
    public String toString() {
        return "ImportHistoryWTPartBean{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", classify='" + classify + '\'' +
                ", version='" + version + '\'' +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                ", oidERPNumber='" + oidERPNumber + '\'' +
                ", container='" + container + '\'' +
                ", locationPath='" + locationPath + '\'' +
                ", type='" + type + '\'' +
                ", partType='" + partType + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                ", ibaMapping=" + ibaMapping +
                '}';
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }
}
