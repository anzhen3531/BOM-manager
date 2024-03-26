package ext.ziang.user.entity;

import java.util.Date;

/**
 * 用户扩展信息
 *
 * @author anzhen
 * @date 2024/03/26
 */
public class UserExtendedInformation {
	private Long id;
	private String username;
	private String password;
	private Integer state;
	private String createdBy;
	private Date createdTime;
	private String modifyBy;
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "UserExtendedInformation{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", state=" + state +
				", createdBy='" + createdBy + '\'' +
				", createdTime=" + createdTime +
				", modifyBy='" + modifyBy + '\'' +
				", modifyTime=" + modifyTime +
				'}';
	}
}
