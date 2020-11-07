package sydney.edu.au;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author king
 * @date 2020-09-30 12:20
 * @description
 */
@MapperScan(value = "sydney.edu.au.web.mapper")
@SpringBootApplication
public class SpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
