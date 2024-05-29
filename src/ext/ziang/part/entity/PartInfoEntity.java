package ext.ziang.part.entity;

import java.sql.Timestamp;

/**
 * 零件信息实体
 *
 * @author anzhen
 * @date 2024/05/29
 */
public class PartInfoEntity {
	/**
	 * oid
	 */
	private String oid;
	/**
	 * 编号
	 */
	private String number;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 版本
	 */
	private String version;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * IPD 状态
	 */
	private String ipdStatus;
	/**
	 * 生命周期
	 */
	private String lifecycle;

	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIpdStatus() {
		return ipdStatus;
	}

	public void setIpdStatus(String ipdStatus) {
		this.ipdStatus = ipdStatus;
	}

	public String getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
}
