package me.zonghua.weapp.weappdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "weapp")
@Valid
public class WeAppConfigProperties {

    @NotEmpty
    private String appId;
    @NotEmpty
    private String secret;

    @NotEmpty
    private String code2SessionUrlTemplate = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode2SessionUrlTemplate() {
        return code2SessionUrlTemplate;
    }

    public void setCode2SessionUrlTemplate(String code2SessionUrlTemplate) {
        this.code2SessionUrlTemplate = code2SessionUrlTemplate;
    }
}
