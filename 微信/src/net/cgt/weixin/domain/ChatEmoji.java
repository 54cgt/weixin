package net.cgt.weixin.domain;

/**
 * 表情对象实体
 * 
 * @author lijian
 * @date 2014-12-11 22:54:28
 */
public class ChatEmoji {

	/**
	 * 表情资源图片对应的ID
	 */
	private int id;
	/**
	 * 表情资源对应的文字描述
	 */
	private String description;
	/**
	 * 表情资源的文件名
	 */
	private String faceName;

	/** 表情资源图片对应的ID */
	public int getId() {
		return id;
	}

	/** 表情资源图片对应的ID */
	public void setId(int id) {
		this.id = id;
	}

	/** 表情资源对应的文字描述 */
	public String getDescription() {
		return description;
	}

	/** 表情资源对应的文字描述 */
	public void setDescription(String character) {
		this.description = character;
	}

	/** 表情资源的文件名 */
	public String getFaceName() {
		return faceName;
	}

	/** 表情资源的文件名 */
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
}
