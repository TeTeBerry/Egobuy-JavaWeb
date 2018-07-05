package com.igeek.egobuy.common.pojo;

import java.util.List;

/**  
* @ClassName: SearchResult  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月21日 下午2:40:38    
* Company www.igeekhome.com
*    
*/
public class SearchResult implements java.io.Serializable{
	private int totalPages;
	private int recourdCount;
	private List<SearchItem> itemList;
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @return the recourdCount
	 */
	public int getRecourdCount() {
		return recourdCount;
	}
	/**
	 * @param recourdCount the recourdCount to set
	 */
	public void setRecourdCount(int recourdCount) {
		this.recourdCount = recourdCount;
	}
	/**
	 * @return the itemList
	 */
	public List<SearchItem> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
}
