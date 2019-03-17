package cmz_finalServer;

import java.io.*;
import java.util.Date;

/**
 * 封装响应信息
 * 类似httPResponse
 */
public class Response implements Serializable {


    private static final long serialVersionUID = 5513741538881943407L;

    //常量
    public static final String CRLF = "\r\n";//字符串常量——换行
    public static final String BLANK = " ";//字符串常量——空格



    //存储响应头信息
    private StringBuilder headInfo;
    //存储正文长度
    private int len = 0;
    //正文响应实体
    private StringBuilder content;
    //给客户端推送的流
    private BufferedWriter bufferedWriter;

    //响应头信息+响应实体
    private StringBuilder resp;

    @Override
    public String toString() {
        resp = resp.append(headInfo.toString()).append(content.toString());
        return resp.toString();
    }

    public StringBuilder getHeadInfo() {
        return headInfo;
    }

    public void setHeadInfo(StringBuilder headInfo) {
        this.headInfo = headInfo;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public StringBuilder getResp() {
        return resp;
    }

    public void setResp(StringBuilder resp) {
        this.resp = resp;
    }

    public Response() {
        headInfo = new StringBuilder();
        content = new StringBuilder();
        resp = new StringBuilder();
        len = 0;

    }
    public Response(OutputStream os) {
        //os为给客户端推送的流,从Socket client = server.accept()
        //client.getOutputStream()这个步骤得到
        this();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));

    }

    /**
     * 构建正文content
     */
    public Response createBody(String info) {
        content.append(info);
        len+=(info).getBytes().length;
        return this;
    }

    /**
     * 构建正文content+回车
     */
    public Response createBodyln(String info){
        content.append(info).append(CRLF);
        len+=(info+CRLF).getBytes().length;//由于构建正文会增加response的长度，所以这里要调整长度
        return this;
    }

    /**
     * 构建响应头
     */
    private void createHeadInfo(int code) {//code为服务器返回给客户端的状态码
        //响应头的http协议版本，状态代码，描述
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 505:
                headInfo.append("Server error");
                break;
        }
        headInfo.append(CRLF);
        //响应头的信息
        headInfo.append("Server:cmz Server/0.0.1").append(CRLF);//服务器版本
        headInfo.append("Date:").append(new Date()).append(CRLF);//日期
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);//数据的编码格式与文本类型
        headInfo.append("Content-Length:").append(len).append(CRLF);//数据的字节长度
        headInfo.append(CRLF);
    }

    public void pushToClient(int code) throws IOException {
        createHeadInfo(code);
        //头信息+CRLF
       bufferedWriter.append(headInfo.toString());//将字符串加到IO流中并写成byte数组
       bufferedWriter.append(content.toString());
       bufferedWriter.flush();
    }
    public void close(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
