package me.zonghua.weapp.weappdemo.service;

import me.zonghua.weapp.weappdemo.response.WeAppLoginResponse;

public interface SessionService {

    void create(WeAppLoginResponse weAppLoginResponse);

    void destroy(String token);

    boolean validate(String token);
}
