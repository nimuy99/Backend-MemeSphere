package com.memesphere.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI MemeSphereAPI() {
        Info info = new Info()
                .title("MemeSphere API")
                .description("""
                        ### MemeSphere API 명세 문서입니다.
                        - #### API 사용 관련 문의는 ~~에 남겨주세요
                        - #### API 관련 버그 및 수정 소요, 제안 등이 있다면 <a href="https://github.com/TeamMemeSphere/Backend-MemeSphere">BE 깃허브</a> 이슈에 등록해주시면 감사하겠습니다.
                        """)
                .version("1.0.0");

        String jwtSchemeName = "JWT TOKEN";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                //.addServersItem(new Server().url("https://15.164.103.195.nip.io"))  // 기본 URL을 nip.io 도메인으로 설정
                .addServersItem(new Server().url("/"))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
