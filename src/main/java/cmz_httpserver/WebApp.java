package cmz_httpserver;

import cmz_httpserver.impl.LoginServlet;
import cmz_httpserver.impl.RegistServlet;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class WebApp {

    private static ServletContext context;

    static{
        context = ServletContext.getInstance();
        Map<String, String> mapping = context.getMapping();
        mapping.put("/login","login");
        mapping.put("/log","login");
        mapping.put("/reg","register");
        Map<String, String> servlet = context.getServlet();
        servlet.put("login", "cmz_httpserver.impl.LoginServlet");
        servlet.put("register","cmz_httpserver.impl.RegistServlet");
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
        //对request的url进行编码处理，并trim()防止有空格出现和map中的key不匹配
        String url = URL.trim();
        System.out.println("url:"+url);
        System.out.println(url.equals("/reg"));
        Map<String, String> map = context.getMapping();
        //遍历map，debug用
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key="+entry.getKey()+","+"Value="+entry.getValue());
        }
        /*
            通过反射来生成servlet对象
         */
        String servletName = map.get(url);
        System.out.println("servletName:"+servletName);
        Map<String, String> map1 = context.getServlet();
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            System.out.println("Key="+entry.getKey()+";"+"Value="+entry.getValue());
        }
        String servletClassName = map1.get(servletName);
        System.out.println("servletClassName:"+servletClassName);//打桩
        Class servletClass = Class.forName(servletClassName);
        Servlet servlet1= (Servlet)servletClass.newInstance();
        return servlet1;
    }
}
