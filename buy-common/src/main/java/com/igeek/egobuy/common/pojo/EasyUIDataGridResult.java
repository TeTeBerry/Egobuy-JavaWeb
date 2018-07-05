package com.igeek.egobuy.common.pojo;

import java.util.List;

/**  
* @ClassName: EasyUIDataGridResult  
* @Description: 专门用来封装EasyUIDataGrid的数据
* @date 2017年11月14日 下午2:07:14    
* Company www.igeekhome.com
*/
public class EasyUIDataGridResult implements java.io.Serializable{
	//总条数
	private Integer total;
	//数据列表
	private List<?> rows;
	
	
	/**  
	* @Title: EasyUIDataGridResult  
	* @param total
	* @param rows    
	*/
	public EasyUIDataGridResult(Integer total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	/**  
	* @Title: EasyUIDataGridResult  
	* @param total
	* @param rows    
	*/
	public EasyUIDataGridResult(Long total, List<?> rows) {
		super();
		this.total = total.intValue();
		this.rows = rows;
	}

	public EasyUIDataGridResult(){
		
	}
	
	
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * @return the rows
	 */
	public List<?> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
