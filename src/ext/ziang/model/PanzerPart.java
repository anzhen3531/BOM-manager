package ext.ziang.model;


import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import wt.part.WTPart;
import wt.util.WTException;

// @formatter:off
@GenAsPersistable(
        superClass = WTPart.class,
        extendable = true,
        properties = {
                @GeneratedProperty(name = "description",
                        type = String.class,
                        javaDoc = "Part description",
                        constraints = @PropertyConstraints(upperLimit = 1024, required = false),
                        columnProperties = @ColumnProperties(index = true)
                ),
                @GeneratedProperty(name = "ipdStatus",
                        type = String.class,
                        javaDoc = "IPD Status",
                        constraints = @PropertyConstraints(upperLimit = 64, required = false),
                        columnProperties = @ColumnProperties(index = true)
                )
        }
)
// @formatter:on
public class PanzerPart extends _PanzerPart {
    static final long serialVersionUID = 1;

    public static PanzerPart newPanzerPart() throws WTException {
        PanzerPart instance = new PanzerPart();
        instance.initialize();
        return instance;
    }
}
