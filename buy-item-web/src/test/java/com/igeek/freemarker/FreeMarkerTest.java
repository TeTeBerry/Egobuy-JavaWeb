package com.igeek.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.igeek.egobuy.item.pojo.Student;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @ClassName: FreeMarkerTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月25日 上午9:27:56 Company www.igeekhome.com
 * 
 */
public class FreeMarkerTest {
	
	
	
	
	@Test
	public void testCreateAllHtml() throws Exception {
		// 第0步：创建一个模板文件
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第二步：设置模板文件所在的路径。 是一个文件夹
		configuration.setDirectoryForTemplateLoading(
				new File("D:/workspace201711/buy-item-web/src/main/webapp/WEB-INF/ftl"));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("UTF-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("student.ftl");
		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map data = new HashMap();
		Student student = new Student(9527, "鸣人", "11011011010");
		data.put("stu", student);
		//集合数据
		List<Student> sts = new ArrayList<>();
		for(int i = 1;i<11;i++){
			sts.add(new Student(9527+i, "鸣人"+i, "110"+i));
		}
		data.put("sts", sts);
		//日期
		Date date = new Date();
		data.put("date", date);
		//设置包含的内部模板的数据
		data.put("hello", "没瞅啥！");
		data.put("query", "电脑!");		
		
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer writer = new FileWriter("D:/igeekhome/freemarker-output/student.html");
		// 第七步：调用模板对象的process方法输出文件。
		template.process(data, writer);
		// 第八步：关闭流。
		writer.close();
	}
	
	
	
	@Test
	public void testCreateHtmlFile() throws Exception {
		// 第0步：创建一个模板文件
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第二步：设置模板文件所在的路径。 是一个文件夹
		configuration.setDirectoryForTemplateLoading(
				new File("D:/workspace201711/buy-item-web/src/main/webapp/WEB-INF/ftl"));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("UTF-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("student.ftl");
		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map data = new HashMap();
		Student student = new Student(9527, "鸣人", "11011011010");
		data.put("stu", student);
		//集合数据
		List<Student> sts = new ArrayList<>();
		for(int i = 1;i<11;i++){
			sts.add(new Student(9527+i, "鸣人"+i, "110"+i));
		}
		data.put("sts", sts);
		//日期
		Date date = new Date();
		data.put("date", date);
		//设置包含的内部模板的数据
		data.put("hello", "没瞅啥！");
		data.put("query", "电脑!");		
		
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer writer = new FileWriter("D:/igeekhome/freemarker-output/student.html");
		// 第七步：调用模板对象的process方法输出文件。
		template.process(data, writer);
		// 第八步：关闭流。
		writer.close();
	}
	
	

	@Test
	public void testCreateFile() throws Exception {
		// 第0步：创建一个模板文件
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第二步：设置模板文件所在的路径。 是一个文件夹
		configuration.setDirectoryForTemplateLoading(
				new File("D:/workspace201711/buy-item-web/src/main/webapp/WEB-INF/ftl"));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("UTF-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("hello.ftl");
		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map data = new HashMap();
		data.put("hello", "瞅你咋地！");
		data.put("query", "秋裤");
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer writer = new FileWriter("D:/igeekhome/freemarker-output/hello.txt");
		// 第七步：调用模板对象的process方法输出文件。
		template.process(data, writer);
		// 第八步：关闭流。
		writer.close();
	}
}
