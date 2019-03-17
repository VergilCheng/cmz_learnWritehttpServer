package cmz_finalServer.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储：
 * <servlet-mapping>
 *         <name>login</name>
 *         <url-pattren>/login</url-pattren>
 * </servlet-mapping>
 */
public class mapping {

    private String name;
    private List<String> urlPattern;

    public mapping() {
        urlPattern = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
