package com.igeek.egobuy.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.egobuy.mapper.TbItemMapper;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemExample;

/**
 * @ClassName: PageHelperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月14日 下午2:25:30 Company www.igeekhome.com
 * 
 */
public class PageHelperTest {
	
	public void testPageHelper() {
		// 创建spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//获取对应的bean
		TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
		//开始查询
		//设置分页相关的参数
		PageHelper.startPage(2, 10); //一旦执行了本行代码，接下来的第一个条查询的sql会被分页
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//输出查询的数据
		System.out.println(list.size());
		//将查询的list包装为一个PageInfo
		PageInfo<TbItem> info = new PageInfo<>(list);
		System.out.println(info.getTotal());
		System.out.println(info.getPages());
		System.out.println(info.getSize());
		System.out.println(info.getLastPage());
	}
}
