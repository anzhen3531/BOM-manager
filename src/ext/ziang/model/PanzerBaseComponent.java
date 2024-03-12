package ext.ziang.model;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.IconProperties;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.part.WTPart;
import wt.util.WTException;

// @formatter:off
@GenAsPersistable(superClass= WTPart.class,
        properties={
                @GeneratedProperty(
                        name="SerialNumber",
                        type=Integer.class,
                        constraints = @PropertyConstraints(upperLimit = 32),
                        javaDoc="SerialNumber"),

                @GeneratedProperty(
                        name="AnotherName",
                        type=String.class,
                        constraints = @PropertyConstraints(upperLimit = 32),
                        javaDoc="AnotherName"),
        },

        iconProperties=@IconProperties(standardIcon="netmarkets/images/stop.gif",
                openIcon="netmarkets/images/stop.gif")
)

// @formatter:on

public class PanzerBaseComponent extends _PanzerBaseComponent {
	static final long serialVersionUID = 1;

	public static PanzerBaseComponent newAcmeModeledDoc()
			throws WTException {
		PanzerBaseComponent instance = new PanzerBaseComponent();
		instance.initialize();
		return instance;
	}
}
