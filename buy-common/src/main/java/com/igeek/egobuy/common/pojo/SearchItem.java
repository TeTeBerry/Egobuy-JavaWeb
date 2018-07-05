package com.igeek.egobuy.common.pojo;

/**  
* @ClassName: SearchItem  
* @Description: 封装查询结果对象
* @date 2017年11月21日 上午11:33:44    
* Company www.igeekhome.com
*    
*/
public class SearchItem implements java.io.Serializable{

	private long id;
	private String title;
	private String sellPoint;
	private long price;
	private String image;
	private String categoryName;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the sellPoint
	 */
	public String getSellPoint() {
		return sellPoint;
	}
	/**
	 * @param sellPoint the sellPoint to set
	 */
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	/**
	 * @return the price
	 */
	public long getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(long price) {
		this.price = price;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the iamge
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param iamge the iamge to set
	 */
	public void setImage(String iamge) {
		this.image = iamge;
	}
	
	
}
