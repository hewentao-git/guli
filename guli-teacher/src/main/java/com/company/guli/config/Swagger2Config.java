package com.company.guli.config;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket webApiConfig(){

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("讲师API")
				.apiInfo(webApiInfo())
				.select()
				.build();

	}

	private ApiInfo webApiInfo(){

		return new ApiInfoBuilder()
				.title("网站-讲师API文档")
				.description("本文档描述了讲师服务接口定义")
				.version("1.0")
//				.contact(new Contact("SQ", "http://www.baidu.com", "1031347185@qq.com"))
				.build();
	}

}
