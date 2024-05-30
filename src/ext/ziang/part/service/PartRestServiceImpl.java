package ext.ziang.part.service;

import java.util.Locale;

import ext.ziang.common.result.R;
import ext.ziang.common.util.ToolUtils;
import ext.ziang.part.entity.PartInfoEntity;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.part.WTPart;
import wt.vc.VersionControlHelper;
import wt.vc.VersionReference;

public class PartRestServiceImpl implements PartRestService {
	/**
	 * 按 OID 查找零件信息
	 *
	 * @param oid
	 *            oid
	 * @return {@link Object }
	 */
	@Override
	public R findPartInfoByOid(String oid) {
		try {
			Persistable persistable = ToolUtils.getObjectByOid(oid);
			if (persistable instanceof WTPart) {
				return R.ok(convertInfoBean((WTPart) persistable));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}
		return R.fail();
	}

	/**
	 * 转换 info bean
	 *
	 * @param part 部分
	 * @return {@link PartInfoEntity }
	 * @throws Exception 例外
	 */
	private PartInfoEntity convertInfoBean(WTPart part) throws Exception {
		PartInfoEntity infoEntity = new PartInfoEntity();
		// infoEntity.setDesc(IBAUtils.getIBAValue(part, ""));
		infoEntity.setName(part.getName());
		infoEntity.setNumber(part.getNumber());
		infoEntity.setVersion(VersionControlHelper.getVersionDisplayIdentifier(part)
				.getLocalizedMessage(Locale.CHINA));
		infoEntity.setLifecycle(part.getLifeCycleName());
		infoEntity.setOid(new ReferenceFactory().getReferenceString(VersionReference.newVersionReference(part)));
		infoEntity.setUnit(part.getDefaultUnit().getDisplay(Locale.CHINA));
		infoEntity.setUpdateTime(part.getModifyTimestamp());
		infoEntity.setCreateBy(part.getCreatorFullName());
		infoEntity.setCreateTime(part.getCreateTimestamp());
		infoEntity.setUpdateBy(part.getModifierFullName());
		return infoEntity;
	}
}
