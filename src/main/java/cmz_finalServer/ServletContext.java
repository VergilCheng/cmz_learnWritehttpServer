package cmz_finalServer;



import java.util.HashMap;
import java.util.Map;

/**
 * 上下文
 */
public class ServletContext {


    //为每一个Servlet取一个别名，例如login——>loginServlet
    private Map<String, String> servlet;
    //url——>login
    private Map<String, String> mapping;

    private static ServletContext instance = new ServletContext();

    public static ServletContext getInstance(){
        return instance;
    }

    private ServletContext() {
        servlet = new HashMap<String, String>();
        mapping = new HashMap<String, String>();
    }




    public Map<String, String> getServlet() {
        return servlet;
    }



    public Map<String, String> getMapping() {
        return mapping;
    }




}
