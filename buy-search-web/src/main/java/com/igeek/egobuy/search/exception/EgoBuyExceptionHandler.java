package com.igeek.egobuy.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**  
* @ClassName: EgoBuyExceptionHandler  
* @Description: 全局异常处理器
* @date 2017年11月22日 下午2:23:18    
* Company www.igeekhome.com
*    
*/
public class EgoBuyExceptionHandler implements HandlerExceptionResolver{
	
	private static final Logger logger = Logger.getLogger(EgoBuyExceptionHandler.class);

	/**  
	* @Title: resolveException  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param request
	* @param response
	* @param handler  正在处理业务的handler   就是controller中的方法对象
	* @param e
	* @return
	* @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	*/
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		//控制台打印异常
		e.printStackTrace();
		//写日志    发短信，发邮件
		logger.debug("出错啦 ");
		logger.info("出错啦");
		logger.error("系统出错", e);
		//重定向、转发  都行。
		//返回一个modelAndView   响应一个错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
