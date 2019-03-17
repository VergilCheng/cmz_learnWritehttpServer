package cmz_finalServer.util;

import cmz_finalServer.Entity.entity;
import cmz_finalServer.Mapping.mapping;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * xml文档解析器
 */
public class WebHandler extends DefaultHandler {

    private List<entity> entityList;
    private List<mapping> mappingList;
    private entity entity;
    private mapping mapping;

    //定义头标签
    private String beginTag;
    //判断是servlet还是map
    private boolean isMap;



    @Override
    public void startDocument() {
        //文档解析开始
        entityList = new ArrayList<entity>();
        mappingList = new ArrayList<mapping>();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        //开始元素
        if(null!=qName){
            beginTag = qName;

            if (qName.equals("servlet")) {
                isMap=false;
                entity = new entity();
            } else if (qName.equals("servlet-mapping")) {
                isMap=true;
                mapping = new mapping();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)  {
        //处理内容
        if (null!=beginTag) {
            String str = new String(ch,start,length);
            if (isMap) {
                if (beginTag.equals("servlet-name")) {
                    mapping.setName(str);
                } else if (beginTag.equals("url-pattern")) {
                    mapping.getUrlPattern().add(str);
                }
            } else {
                if (beginTag.equals("servlet-name")) {
                    entity.setName(str);
                } else if (beginTag.equals("servlet-class")) {
                    entity.setClazz(str);
                }
            }
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       //结束元素
        if(null!=qName){
            if (qName.equals("servlet")) {
                entityList.add(entity);
                entity = new entity();
            } else if (qName.equals("servlet-mapping")) {
                mappingList.add(mapping);
            }
        }
    }
    @Override
    public void endDocument() throws SAXException {
        //文档解析结束

    }

    @Override
    public String toString() {
        return "WebHandler{" +
                "entityList=" + entityList +
                ", mappingList=" + mappingList +
                '}';
    }

    public List<cmz_finalServer.Entity.entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<cmz_finalServer.Entity.entity> entityList) {
        this.entityList = entityList;
    }

    public List<cmz_finalServer.Mapping.mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<cmz_finalServer.Mapping.mapping> mappingList) {
        this.mappingList = mappingList;
    }

    /**
     * 测试我们的方法是否能够正确解析xml文件
     * @param args
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser sax = factory.newSAXParser();
        WebHandler handler  = new WebHandler();
        ClassLoader loader = WebHandler.class.getClassLoader();
        //TODO:resourceAsStream输入流为空
        InputStream resourceAsStream = loader.getResourceAsStream("cmz_finalServer/util/web.xml");
        sax.parse(resourceAsStream,handler);
        System.out.println(handler.toString());
    }
}
