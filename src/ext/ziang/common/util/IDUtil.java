package ext.ziang.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 生成id工具
 * 
 * @author anzhen
 * @date 2023/12/03
 */
public class IDUtil {
	private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

	public static Long getNextId() {
		return SNOWFLAKE.nextId();
	}
}
