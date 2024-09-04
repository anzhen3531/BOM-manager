package ext.ziang.report.model;

import com.ptc.windchill.annotations.metadata.*;

import wt.fc.WTObject;
import wt.util.WTException;

/**
 * 建模快速命令
 * <p>
 * 遍历Class文件 ant -f bin/tools.xml class -Dclass.includes=ext/ziang/report/model/*
 * <p>
 * 生成SQL脚本 ant -f bin/tools.xml sql_script -Dgen.input=ext.ziang.report.model.*
 * <p>
 * @ext/ziang/report/model/create_ReportFormConfig_Table.sql
 * <p>
 * @ext/ziang/report/model/create_ReportFormConfig_Index.sql
 */
// @formatter:off
@GenAsPersistable(
        superClass = WTObject.class,
        properties = {@GeneratedProperty(
                name = "description",
                type = String.class,
                supportedAPI = SupportedAPI.PUBLIC,
                javaDoc = "当前SQL的描述",
                constraints = @PropertyConstraints(
                        stringCase = StringCase.UPPER_CASE,
                        changeable = Changeable.VIA_OTHER_MEANS,
                        upperLimit = 200,
                        required = true
                ),
                columnProperties = @ColumnProperties(
                        columnName = "description"
                )),
                @GeneratedProperty(
                name = "content",
                type = String.class,
                supportedAPI = SupportedAPI.PUBLIC,
                javaDoc = "内容",
                constraints = @PropertyConstraints(
                        stringCase = StringCase.UPPER_CASE,
                        changeable = Changeable.VIA_OTHER_MEANS,
                        upperLimit = 2000,
                        required = true
                ),
                columnProperties = @ColumnProperties(
                        columnName = "SqlScript"
                )),
                @GeneratedProperty(
                        name = "state",
                        type = Integer.class,
                        supportedAPI = SupportedAPI.PUBLIC,
                        javaDoc = "状态 1为开启 0为关闭",
                        constraints = @PropertyConstraints(
                                upperLimit = 2,
                                required = true
                        ),
                        columnProperties = @ColumnProperties(
                                columnName = "state"
                ))
        }
)
// @formatter:on
public class ReportFormConfig extends _ReportFormConfig {
    static final long serialVersionUID = 1L;

    public static ReportFormConfig newReportFormConfig() throws WTException {
        final ReportFormConfig reportFormConfig = new ReportFormConfig();
        reportFormConfig.initialize();
        return reportFormConfig;
    }
}
