package com.igeek.egobuy.item.pojo;

/**  
* @ClassName: Student  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月25日 上午10:03:45    
* Company www.igeekhome.com
*    
*/
public class Student {
	private int id;
	private String name;
	private String tel;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**  
	* @Title: Student  
	* @param id
	* @param name
	* @param tel    
	*/
	public Student(int id, String name, String tel) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
	}
	/**  
	* @Title: Student      
	*/
	public Student() {
		super();
	}
	
}
