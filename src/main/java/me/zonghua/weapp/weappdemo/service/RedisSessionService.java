package me.zonghua.weapp.weappdemo.service;

import me.zonghua.weapp.weappdemo.response.WeAppLoginResponse;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.UUID;

public class RedisSessionService implements SessionService {
    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisSessionService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void create(WeAppLoginResponse weAppLoginResponse) {
        weAppLoginResponse.setToken(UUID.randomUUID().toString());
        redisTemplate.boundValueOps(weAppLoginResponse.getToken()).set(weAppLoginResponse, Duration.ofMinutes(1));
    }

    @Override
    public void destroy(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public boolean validate(String token) {
        WeAppLoginResponse res = (WeAppLoginResponse) redisTemplate.boundValueOps(token).get();
        return res != null;
    }
}
