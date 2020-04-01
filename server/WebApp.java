package server;

import servlet.WebContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
    private static WebContext webContext;
    static {
        try {
            //1.获取解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2.从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();
            //3.编写处理器
            WebHandler handler = new WebHandler();
            //4.加载文档，解析
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), handler);
            //5.数据处理
            webContext = new WebContext(handler.getEntities(), handler.getMappings());
        } catch (Exception e) {
            System.out.println("解析配置文件错误！");
        }
    }

    /**
     * 通过url获取配置文件对应的servlet
     * @param url
     * @return
     */
    public static Servlet getServletFromUrl(String url) {
        try {
            //从配置文件获取classname后用反射得到class
            String className = webContext.getClassName(url);
            Class c = Class.forName(className);
            Servlet servlet = (Servlet) c.getDeclaredConstructor().newInstance();
            return servlet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
