package me.zonghua.weapp.weappdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    /**
     * 微信响应的不是标准的json
     *
     * @return 支持 解析 text/plain 的工具
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<MediaType> supportMediaType = new ArrayList<>();
        supportMediaType.add(MediaType.TEXT_PLAIN);
        supportMediaType.add(MediaType.APPLICATION_JSON);
        supportMediaType.add(new MediaType("application", "*+json"));
        for (HttpMessageConverter<?> messageConverter : restTemplate.getMessageConverters()) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) messageConverter).setSupportedMediaTypes(supportMediaType);
            }
        }
        return restTemplate;
    }

}
