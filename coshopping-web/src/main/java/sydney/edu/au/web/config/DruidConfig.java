package sydney.edu.au.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author king
 * @date 2020-09-30 13:20
 * @description
 */

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix= "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }

}
