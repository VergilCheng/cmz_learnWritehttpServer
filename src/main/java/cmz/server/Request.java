package cmz.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class Request {

    //请求方式

    @Override
    public String toString() {
        return requestInfo;
    }

    public String getMethord() {
        return methord;
    }

    public void setMethord(String methord) {
        this.methord = methord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    private String methord;
    //请求资源url
    private String url;
    //请求参数,发送给服务器的数据
    private Map<String,List<String>> parameterMapValues;

    //常量，回车
    public static final String CRLF = "\r\n";//字符串常量——回车
    public static final String BLANK = " ";//字符串常量——空格

    //内部使用的流
    private InputStream is;
    //内部封装好的请求信息
    private String requestInfo;

    public Request() {
        //避免空指针异常
        methord = "";
        url = "";
        parameterMapValues = new HashMap<String, List<String>>();
    }

    public Request(InputStream is) {
        this();
        this.is = is;

        try {
            byte[] data = new byte[20480];
            int len = 0;
            len = is.read(data);
            requestInfo = new String(data,0,len);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //分析头信息
        parseRequestInfo();

    }

    /**
     * 分析头信息，将头信息封装到request的属性中
     */
    private void parseRequestInfo() {
        if(requestInfo==null||(requestInfo.trim()).equals("")){
            return;
        }
        /**
         * 从信息的首行分解出：请求方式，请求路径，请求参数（get可能存在）
         *
         * 如果为post方式，请求参数可能在最后正文中
         */
        String paramString = "";//接受请求参数
        //1.获取请求方式
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
        //System.out.println("firstLine:"+firstLine);//打桩
        int index = requestInfo.indexOf("/");
        this.methord = firstLine.substring(0,index).trim();
        //System.out.println("methord:"+methord);//打桩
        String urlStr = firstLine.substring(index,firstLine.indexOf("HTTP/"));
        //System.out.println("urlStr:"+urlStr);//打桩
        //System.out.println("*************");//分割线
        if (this.methord.equals("POST")) {//equals的忽略大小写方法
            this.url = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if(this.methord.equals("GET")){
            //System.out.println("进入get方法的if");//打桩
            if(urlStr.contains("?")){//get方法是否存在参数
                //System.out.println("get方法有参数");//打桩
                String[] urlArray = urlStr.split("\\?");//问好的转移字符是\\?
                this.url = urlArray[0];
                //System.out.println("url:"+url);//打桩
                paramString = urlArray[1];//接受请求参数
                //System.out.println("paramString:"+paramString);//打桩
            }else{
                this.url = urlStr;
            }
        }

        //2.将请求存储到map中
        if(paramString==null||"".equals(paramString)){
            return;
        }
        setParams(paramString);

    }

    private void setParams(String paramString){
        //分割 将字符串转换成数组
        //StringTokenizer:一个String工具类，内置了迭代器方法
        StringTokenizer token = new StringTokenizer(paramString,"&");//将请求参数按照&分割，类似String的split方法
        while (token.hasMoreTokens()) {
            String keyvalue = token.nextToken();
            String[] keyValues = keyvalue.split("=");
            if(keyValues.length==1){
                //如果我们输入的get的url后缀的参数只有key没有value，或者只有value没有key的情况
                keyValues = Arrays.copyOf(keyValues,2);
                keyValues[1]=null;
            }
            //转换成map
            String key = keyValues[0].trim();
            String value = (null==keyValues[1]?null:decode(keyValues[1].trim(),"utf-8"));
            List<String> values = new ArrayList<String>();
            if (!parameterMapValues.containsKey(key)) {
                parameterMapValues.put(key,values);
                values.add(value);
            }else{
                List oldvalues = parameterMapValues.get(key);
                oldvalues.add(value);
            }

        }

    }

    /**
     * 使用decode函数对中文字符串进行解码，防止乱码
     */
    private String decode(String value,String code) {
        try {
            return java.net.URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 根据页面的name获取对应的单个值
     */
    public String getParameter(String name) {
        if(parameterMapValues.get(name)==null||parameterMapValues.get(name).isEmpty()){
            return null;
        }else{
            return parameterMapValues.get(name).get(0);
        }
    }
    /**
     * 根据页面的name获取对应的所有list值
     */
    public String[] getParameters(String name) {
        if(parameterMapValues.get(name)==null||parameterMapValues.get(name).isEmpty()){
            return null;
        }else{
            return (String[]) parameterMapValues.get(name).toArray();
        }
    }

}
