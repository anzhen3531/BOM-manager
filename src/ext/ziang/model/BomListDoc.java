package ext.ziang.model;


import wt.doc.WTDocument;
import wt.util.WTException;
import com.ptc.windchill.annotations.metadata.*;

@GenAsPersistable(superClass=WTDocument.class,
        properties={
				@GeneratedProperty(
						name="SerialNumber",
						type=Integer.class,
						constraints = @PropertyConstraints(upperLimit = 32, required = true),
						javaDoc="SerialNumber"),

				@GeneratedProperty(
						name="ContractNumber",
						type=String.class,
						constraints = @PropertyConstraints(upperLimit = 32, required = true),
						javaDoc="ContractNumber"),
		},

        iconProperties=@IconProperties(standardIcon="netmarkets/images/stop.gif",
                openIcon="netmarkets/images/stop.gif")
)
public class BomListDoc  extends _BomListDoc {
    static final long serialVersionUID = 1;
    public static BomListDoc newAcmeModeledDoc()
            throws WTException {
        BomListDoc instance = new BomListDoc();
        instance.initialize();
        return instance;
    }

}
