package me.zonghua.weapp.weappdemo.service;

import com.alibaba.fastjson.JSON;
import me.zonghua.weapp.weappdemo.TestHelper;
import me.zonghua.weapp.weappdemo.WeappDemoApplication;
import me.zonghua.weapp.weappdemo.config.WeAppConfigProperties;
import me.zonghua.weapp.weappdemo.exception.WeAppException;
import me.zonghua.weapp.weappdemo.request.WeAppLoginRequest;
import me.zonghua.weapp.weappdemo.response.Code2SessionResponse;
import me.zonghua.weapp.weappdemo.response.DecryptedUserInfo;
import me.zonghua.weapp.weappdemo.response.WeAppLoginResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {WeappDemoApplication.class})
class WeAppAuthenticationServiceTest {

    @Autowired
    private WeAppAuthenticationService appAuthenticationService;

    @Autowired
    private WeAppConfigProperties weAppConfigProperties;

    @Test
    void code2SessionHasBeanUsed() {
        try {
            Code2SessionResponse res = appAuthenticationService.code2Session("033CnGwb1KHhZw0tZfxb1Rs1xb1CnGwQ");
        } catch (Exception e) {
            assert e instanceof WeAppException;
            Assertions.in(((WeAppException) e).getErrorCode(), 40163, 40029);
        }
    }

    @Test
    void code2SessionBadRequest() {
        String preUrl = weAppConfigProperties.getCode2SessionUrlTemplate();
        weAppConfigProperties.setCode2SessionUrlTemplate("http://applehater.cn");
        try {
            Code2SessionResponse res = appAuthenticationService.code2Session("033CnGwb1KHhZw0tZfxb1Rs1xb1CnGwQ");
        } catch (Exception e) {
            assert e instanceof WeAppException;
            assertEquals(null, ((WeAppException) e).getErrorCode());
        } finally {
            weAppConfigProperties.setCode2SessionUrlTemplate(preUrl);
        }
    }

    @Test
    void decrypt() throws Exception {
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "hpNICkosEDxJG70UF1nffQ==";
        String encryptedData = "6Iq86z8WewqcZ6slYEXGON7cSFQ1oxml8wR7Mwflua+ygiqPIX42f3rmhF4jd3FIELT6gmgW713d5JST9nQEiwsxinQ0cxpU+P6ccnO9I8OrKGjLEkSZEqFrqGYiWjbvqfmyOqyaQkZ0YH71rOIwvpGET5pH7ej1sEvC2EWMj0MqNxXzYpKXmZaSlGUuWgLu/6wg9qQcBFCMZkpyLPbVGURChyvIrjJiI2zLgxvmXlEXDEjd5AX5kFurqnJjSM6EKrUeXVnLkrNpGjWFeQvUDE7ign7TLWQyK+MDOyH3n1J+qZ4GGFQZ17+EBLU49SYs+zk1Lh98KuRRyvIxjD9mDD43T2geUWMwbZoq0KWZyqE2D+1Lax0w3DeA6Uf4g7nNRLafPQyJhX8KNsNVmUXPPtvQs6Z2g7uwgDI9rivQVwyMCVWY7sWwjmULycFTDh6lpnz5j0SAC+TaCw+MrbJmkLBwiunbTatXHzSuwWP1fYM=";
        String iv = "BE5BTSLFSPkVD9GbYIPnGw==";
        String res = appAuthenticationService.decrypt(encryptedData, iv, sessionKey);
        assertEquals(TestHelper.readTestContent("testDecryptedUserInfo.json"), res);
    }

    @Test
    void decryptUserInfo() {
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        WeAppLoginRequest weAppLoginRequest = new WeAppLoginRequest();
        weAppLoginRequest.setUserInfo(new WeAppLoginRequest.UserInfoBeanX() {
            {
                setEncryptedData(encryptedData);
                setIv(iv);
            }
        });
        Code2SessionResponse code2SessionResponse = new Code2SessionResponse();
        code2SessionResponse.setSessionKey(sessionKey);
        weAppConfigProperties.setAppId(appId);
        DecryptedUserInfo res = appAuthenticationService.decryptUserInfo(weAppLoginRequest, code2SessionResponse);
        assertEquals("ocMvos6NjeKLIBqg5Mr9QjxrP1FA", res.getUnionId());
        assertEquals("oGZUI0egBJY1zhBYw2KhdUfwVJJE", res.getOpenId());
        assertEquals("Band", res.getNickName());
    }

    @Test
    void decryptUserInfoWithPadding() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidParameterSpecException, NoSuchProviderException, IOException, URISyntaxException {
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "hpNICkosEDxJG70UF1nffQ==";
        String encryptedData = "6Iq86z8WewqcZ6slYEXGON7cSFQ1oxml8wR7Mwflua+ygiqPIX42f3rmhF4jd3FIELT6gmgW713d5JST9nQEiwsxinQ0cxpU+P6ccnO9I8OrKGjLEkSZEqFrqGYiWjbvqfmyOqyaQkZ0YH71rOIwvpGET5pH7ej1sEvC2EWMj0MqNxXzYpKXmZaSlGUuWgLu/6wg9qQcBFCMZkpyLPbVGURChyvIrjJiI2zLgxvmXlEXDEjd5AX5kFurqnJjSM6EKrUeXVnLkrNpGjWFeQvUDE7ign7TLWQyK+MDOyH3n1J+qZ4GGFQZ17+EBLU49SYs+zk1Lh98KuRRyvIxjD9mDD43T2geUWMwbZoq0KWZyqE2D+1Lax0w3DeA6Uf4g7nNRLafPQyJhX8KNsNVmUXPPtvQs6Z2g7uwgDI9rivQVwyMCVWY7sWwjmULycFTDh6lpnz5j0SAC+TaCw+MrbJmkLBwiunbTatXHzSuwWP1fYM=";
        String iv = "BE5BTSLFSPkVD9GbYIPnGw==";
        String res = appAuthenticationService.decrypt(encryptedData, iv, sessionKey);
        assertEquals(TestHelper.readTestContent("testDecryptedUserInfo.json"), res);
    }

    //    @Test
    public void login() throws IOException, URISyntaxException {
        String requestData = TestHelper.readTestContent("testLoginRequest.json");
        WeAppLoginRequest weAppLoginRequest = JSON.parseObject(requestData, WeAppLoginRequest.class);
        WeAppLoginResponse res = appAuthenticationService.login(weAppLoginRequest);
    }
}