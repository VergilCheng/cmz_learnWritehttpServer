package cmz_finalServer.Entity;

/**
 * 存储：
 *  <servlet>
 *         <servlet-name>login</servlet-name>
 *         <servlet-class>cmz_finalServer.impl.LoginServlet</servlet-class>
 *   </servlet>
 */
public class entity {
    private String name;
    private String clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
