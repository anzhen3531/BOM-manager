package ext.ziang.model;


import com.ptc.windchill.annotations.metadata.*;
import wt.part.WTPart;
import wt.util.WTException;

// @formatter:off
@GenAsPersistable(
        superClass = WTPart.class,
        extendable = true,
        foreignKeys = {@GeneratedForeignKey(
            foreignKeyRole = @ForeignKeyRole(
                    name = "master",
                    type = PanzerPartMaster.class,
                    supportedAPI = SupportedAPI.PUBLIC,
                    cascade = false,
                    constraints = @PropertyConstraints(
                            required = true
                    )
            ),
            myRole = @MyRole(
                    name = "iteration",
                    supportedAPI = SupportedAPI.PUBLIC,
                    cascade = false
            )
        )},
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
