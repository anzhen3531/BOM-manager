package ext.ziang.part.service;

import ext.ziang.common.result.Result;

public interface PartRestService {
	/**
	 * 按 OID 查找零件信息
	 *
	 * @param oid
	 *            oid
	 * @return {@link Object }
	 */
	Result findPartInfoByOid(String oid);
}
