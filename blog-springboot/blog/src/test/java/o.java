import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@SpringBootTest
@ConfigurationProperties(prefix = "m")//指定读取application.yml文件的myoss属性的数据
public class o {
    //注意要从application.yml读取属性数据，下面的3个成员变量的名字必须对应application.yml的myoss属性的三个子属性名字
    private String a;
    private String b;

    public void setXxaccessKey(String a) {
        this.a = a;
    }

    public void setXxsecretKey(String b) {
        this.b = b;
    }

    @Test
    public void testOss() {
        //构造一个带指定 Region 对象的配置类。你的七牛云OSS创建的是哪个区域的，那么就调用Region的什么方法即可
        System.out.println(a);
    }
}