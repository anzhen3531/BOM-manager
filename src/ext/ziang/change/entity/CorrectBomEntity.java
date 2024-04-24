package ext.ziang.change.entity;

/**
 * 正确物料清单实体
 *
 * @author anzhen
 * @date 2024/04/19
 */
public class CorrectBomEntity {
	/**
	 * 数
	 */
	String number;

	/**
	 * oid
	 */
	String oid;

	/**
	 * 名字
	 */
	String name;

	/**
	 * 描述
	 */
	String description;

	/**
	 * 是否是替代
	 */
	String isSubstitute = "false";

	/**
	 * 是否是替代
	 */
	String substitutePart;

	/**
	 * 版本
	 */
	String version;

	/**
	 * 单位
	 */
	String defaultUnit;

	/**
	 * 量
	 */
	String amount;

	/**
	 * 造物主
	 */
	String creator;
	/**
	 * 修饰语
	 */
	String modifier;
	/**
	 * 创建图章
	 */
	String createStamp;

	/**
	 * 修改图章
	 */
	String modifyStamp;

	boolean isSelect;


	public String getSubstitutePart() {
		return substitutePart;
	}

	public void setSubstitutePart(String substitutePart) {
		this.substitutePart = substitutePart;
	}

	public String getIsSubstitute() {
		return isSubstitute;
	}

	public void setIsSubstitute(String isSubstitute) {
		this.isSubstitute = isSubstitute;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDefaultUnit() {
		return defaultUnit;
	}

	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getCreateStamp() {
		return createStamp;
	}

	public void setCreateStamp(String createStamp) {
		this.createStamp = createStamp;
	}

	public String getModifyStamp() {
		return modifyStamp;
	}

	public void setModifyStamp(String modifyStamp) {
		this.modifyStamp = modifyStamp;
	}

	@Override
	public String toString() {
		return "CorrectBomEntity{" +
				"number='" + number + '\'' +
				", oid='" + oid + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", isSubstitute='" + isSubstitute + '\'' +
				", substitutePart='" + substitutePart + '\'' +
				", version='" + version + '\'' +
				", defaultUnit='" + defaultUnit + '\'' +
				", amount='" + amount + '\'' +
				", creator='" + creator + '\'' +
				", modifier='" + modifier + '\'' +
				", createStamp='" + createStamp + '\'' +
				", modifyStamp='" + modifyStamp + '\'' +
				", isSelect=" + isSelect +
				'}';
	}
}
