package sydney.edu.au.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author king
 * @date 2020-09-30 15:02
 * @description
 */
@Configuration
@EnableSwagger2  //开启swagger2
public class SwaggerConfig {

    //配置多个分组
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
    }

    //配置docket以配置Swagger具体参数
    @Bean
    //如何动态配置当项目处于test、dev环境时显示swagger，处于prod时不显示？
    public Docket docket(Environment environment) {

        // 设置要显示swagger的环境
        Profiles profiles = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //enable是否启用enable
                //.groupName("hello") // 配置分组
                .enable(flag)
                .select()
                // 通过.select()方法，去配置扫描接口
                // RequestHandlerSelectors配置如何扫描接口
                // any()扫描全部
                // none()都不扫描
                // withClassAnnotation()扫描类上的注解，参数是一个注解的反射对象
                // withMethodAnnotation()扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("sydney.edu.au.web.controller"))
                //paths()过滤什么路径,即这里只扫描请求以/kuang开头的接口
                //.paths(PathSelectors.ant("/kuang/**"))
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("King",
                null, "947079912@qq.com");
        return new ApiInfo(
                "co-shopping", // 标题
                "co-shopping API document", // 描述
                "v1.0", // 版本
                null, // 组织链接
                contact, // 联系人信息
                null, // 许可
                null, // 许可连接
                new ArrayList<>()// 扩展
        );
    }

}
