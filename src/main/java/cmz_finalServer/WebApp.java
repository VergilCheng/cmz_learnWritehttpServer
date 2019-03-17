package cmz_finalServer;

import cmz_finalServer.Entity.entity;
import cmz_finalServer.Mapping.mapping;
import cmz_finalServer.impl.LoginServlet;
import cmz_finalServer.impl.RegistServlet;
import cmz_finalServer.util.WebHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 模拟一个项目
 */
public class WebApp {

    private static ServletContext context;

    //这个static块用来加载有效路径和我们要配置的servlet
    //但是不方便我们进行修改，如果我们新建了一个servlet
    //这个类就又需要修改，并重新加载，非常麻烦并消耗资源
    //所以我们需要配置文件！！！
    static{
        try {
            //读取web.xml文件，解析我们需要创建的servlet
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sax = factory.newSAXParser();
            Thread t = Thread.currentThread();
            WebHandler handler  = new WebHandler();
            ClassLoader loader = t.getContextClassLoader();
            InputStream resourceAsStream = loader.getResourceAsStream("cmz_finalServer/util/web.xml");
            sax.parse(resourceAsStream,handler);
            //将list转换成map
            context = ServletContext.getInstance();
            Map<String,String> servlet = context.getMapping();
            for (entity entity : handler.getEntityList()) {
                //存储servlet-name为key，servlet-class为类的全称
                servlet.put(entity.getName(),entity.getClazz());
            }
            Map<String,String> Mapping = context.getServlet();
            for (mapping map :handler.getMappingList()) {
                List<String> urls = map.getUrlPattern();
                for (String url:urls) {
                    Mapping.put(url,map.getName());
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url返回对应的servlet
     * @param URL
     * @return
     */
    public static Servlet getServlet(String URL) throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(null==URL||(URL.trim()).equals("")){
            return null;
        }
        String url = URL.trim();
        System.out.println("url:"+url);
        System.out.println(url.equals("/reg"));
        Map<String, String> map = context.getMapping();
        //遍历map，debug用

        //TODO:WebHandler读取xml文件得不到对应的标签，读取到并放入到mapping中的是空字符串,查看尚学堂视频解决这个问题
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key="+entry.getKey().trim()+","+"Value="+entry.getValue().trim());
        }
        //通过反射来生成指定的servlet对象
        String servletName = map.get(url);//servletName为空,根本原因为34行我们得到的url与map中key的url不一样
        Map<String, String> map1 = context.getServlet();
        String servletClassName = map1.get(servletName);
        Class servletClass = Class.forName(servletClassName);
        Servlet servlet1= (Servlet)servletClass.newInstance();
        return servlet1;
    }
}
