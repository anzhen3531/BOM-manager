package ext.ziang.part.model.derive;

import com.ptc.windchill.annotations.metadata.*;

import wt.fc.ObjectToObjectLink;
import wt.part.WTPartMaster;
import wt.util.WTException;

/**
 * 建模快速命令
 *<p>
 * ant -f bin/tools.xml class -Dclass.includes=ext/ziang/part/model/*
 * <p>
 * ant -f bin/tools.xml sql_script -Dgen.input=ext.ziang.part.model.*
 *
 */
// @formatter:off
@GenAsBinaryLink(
        superClass = ObjectToObjectLink.class,
        roleA = @GeneratedRole(
                name = "deriveFor",
                type = WTPartMaster.class,
                supportedAPI = SupportedAPI.PUBLIC
        ),
        roleB = @GeneratedRole(
                name = "derives",
                type = WTPartMaster.class,
                supportedAPI = SupportedAPI.PUBLIC
        ),
        properties = {
                @GeneratedProperty(name = "state",
                        type = String.class,
                        constraints = @PropertyConstraints(upperLimit = 64, required = true),
                        supportedAPI = SupportedAPI.PUBLIC, javaDoc = "状态"),
        },
        // 直接编写编号 为了直接处理
        tableProperties = @TableProperties(
                compositeUnique1 = "roleAObjectRef.key.id+roleBObjectRef.key.id",
                tableName = "PartDeriveLink",
                oracleTableSize = OracleTableSize.LARGE
        )
)
// @formatter:on
public class PartDeriveLink extends _PartDeriveLink {
    /**
     * 序列化id
     */
    static final long serialVersionUID = 1L;

    public static PartDeriveLink newPartDeriveLink() throws WTException {
        final PartDeriveLink partDeriveLink = new PartDeriveLink();
        partDeriveLink.initialize();
        return partDeriveLink;
    }
}
