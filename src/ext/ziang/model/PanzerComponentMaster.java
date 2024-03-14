package ext.ziang.model;


import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.mpml.MPMCompatibleMaster;
import wt.part.WTPartMaster;

@GenAsPersistable(
        superClass = WTPartMaster.class,
        interfaces = {MPMCompatibleMaster.class},
        serializable = Serialization.EXTERNALIZABLE_BASIC
)
public class PanzerComponentMaster {
}
