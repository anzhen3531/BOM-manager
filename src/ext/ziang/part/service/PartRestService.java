package ext.ziang.part.service;

import ext.ziang.common.result.R;
import ext.ziang.part.entity.PartInfoEntity;

public interface PartRestService {
	/**
	 * 按 OID 查找零件信息
	 *
	 * @param oid
	 *            oid
	 * @return {@link Object }
	 */
	R findPartInfoByOid(String oid);
}
