package ext.ziang.model;


import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;

// @formatter:off
@GenAsPersistable(
        superClass = WTPartMaster.class,
        extendable = true
)
// @formatter:on
public class PanzerPartMaster extends _PanzerPartMaster {
    static final long serialVersionUID = 1;

    public static PanzerPartMaster newPanzerPartMaster() throws WTException {
        PanzerPartMaster master = new PanzerPartMaster();
        master.initialize();
        return master;
    }
}
