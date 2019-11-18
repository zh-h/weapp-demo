package me.zonghua.weapp.weappdemo.config;

import me.zonghua.weapp.weappdemo.service.RedisSessionService;
import me.zonghua.weapp.weappdemo.service.SessionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@EnableConfigurationProperties(WeAppConfigProperties.class)
@Configuration
public class WeAppConfig {

    @Bean
    @ConditionalOnMissingBean(SessionService.class)
    public SessionService sessionService(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisSessionService(redisTemplate);
    }
}
