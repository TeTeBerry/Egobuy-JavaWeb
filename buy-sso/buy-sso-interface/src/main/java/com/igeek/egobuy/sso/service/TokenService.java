package com.igeek.egobuy.sso.service;

import com.igeek.egobuy.common.utils.BuyResult;

/**  
* @ClassName: TokenService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午11:46:39    
* Company www.igeekhome.com
*    
*/
public interface TokenService {

	/**
	 * 
	* @Title: getUserByToken  
	* @Description: 通过token获取用户信息
	* @param token
	* @return
	 */
	public BuyResult getUserByToken(String token);
}
