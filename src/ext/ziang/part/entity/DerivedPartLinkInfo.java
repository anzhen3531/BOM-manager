package ext.ziang.part.entity;

import com.ptc.netmarkets.model.NmOid;

/**
 * 
 */
public class DerivedPartLinkInfo extends NmOid {
    private String derivedForName;
    private String derivedForNumber;
    private String derivesNumber;
    private String derivesName;
    private String state;

    @Override
    public String toString() {
        return "DerivedPartLinkInfo{" + "derivedForName='" + derivedForName + '\'' + ", derivedForNumber='"
            + derivedForNumber + '\'' + ", derivesNumber='" + derivesNumber + '\'' + ", derivesName='" + derivesName
            + '\'' + ", state='" + state + '\'' + ", ref=" + ref + '}';
    }

    public String getDerivedForName() {
        return derivedForName;
    }

    public void setDerivedForName(String derivedForName) {
        this.derivedForName = derivedForName;
    }

    public String getDerivedForNumber() {
        return derivedForNumber;
    }

    public void setDerivedForNumber(String derivedForNumber) {
        this.derivedForNumber = derivedForNumber;
    }

    public String getDerivesNumber() {
        return derivesNumber;
    }

    public void setDerivesNumber(String derivesNumber) {
        this.derivesNumber = derivesNumber;
    }

    public String getDerivesName() {
        return derivesName;
    }

    public void setDerivesName(String derivesName) {
        this.derivesName = derivesName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
