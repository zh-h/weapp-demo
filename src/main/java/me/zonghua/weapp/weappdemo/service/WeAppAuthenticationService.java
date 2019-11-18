package me.zonghua.weapp.weappdemo.service;

import com.alibaba.fastjson.JSON;
import me.zonghua.weapp.weappdemo.config.WeAppConfigProperties;
import me.zonghua.weapp.weappdemo.exception.WeAppException;
import me.zonghua.weapp.weappdemo.request.WeAppLoginRequest;
import me.zonghua.weapp.weappdemo.response.Code2SessionResponse;
import me.zonghua.weapp.weappdemo.response.DecryptedUserInfo;
import me.zonghua.weapp.weappdemo.response.WeAppLoginResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeAppAuthenticationService {
    private final RestTemplate restTemplate;

    private final SessionService sessionService;

    private final WeAppConfigProperties weAppConfigProperties;


    private static final Logger logger = LoggerFactory.getLogger(WeAppAuthenticationService.class);

    @Autowired
    public WeAppAuthenticationService(RestTemplate restTemplate, SessionService sessionService, WeAppConfigProperties weAppConfigProperties) {
        this.restTemplate = restTemplate;
        this.sessionService = sessionService;
        this.weAppConfigProperties = weAppConfigProperties;
        initialize();
    }

    private static void initialize() {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * 登录
     * 要在code2Session后面返回的才能用
     *
     * @param weAppLoginRequest 登录请求
     * @return 响应
     */
    public WeAppLoginResponse login(WeAppLoginRequest weAppLoginRequest) {
        Code2SessionResponse code2SessionResponse = code2Session(weAppLoginRequest.getCode());
        DecryptedUserInfo decryptUserInfo = decryptUserInfo(weAppLoginRequest, code2SessionResponse);
        WeAppLoginResponse weAppLoginResponse = WeAppLoginResponse.fromDecryptedUserInfo(decryptUserInfo);
        sessionService.create(weAppLoginResponse);
        return weAppLoginResponse;
    }


    public void logout(String token) {
        sessionService.destroy(token);
    }

    Code2SessionResponse code2Session(String code) {
        Map<String, String> param = new HashMap<>(3);
        param.put("appid", weAppConfigProperties.getAppId());
        param.put("secret", weAppConfigProperties.getSecret());
        param.put("js_code", code);
        try {
            Code2SessionResponse res = restTemplate.getForObject(weAppConfigProperties.getCode2SessionUrlTemplate(), Code2SessionResponse.class, param);
            if (res.getErrCode() == null || res.getErrCode() == 0) {
                return res;
            }
            throw new WeAppException(res.getErrCode(), res.getErrMsg());
        } catch (RestClientException e) {
            throw new WeAppException(e.getMessage(), e);
        }
    }

    String decrypt(String encryptedData, String iv, String sessionKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        logger.debug("encryptedData: {}", encryptedData);
        logger.debug("iv: {}", iv);
        logger.debug("sessionKey {}", sessionKey);
        byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (sessionKeyBytes.length % base != 0) {
            int groups = sessionKeyBytes.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(sessionKeyBytes, 0, temp, 0, sessionKeyBytes.length);
            sessionKeyBytes = temp;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(sessionKeyBytes, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivBytes));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultBytes = cipher.doFinal(encryptedDataBytes);
        return new String(resultBytes);
    }

    DecryptedUserInfo decryptUserInfo(WeAppLoginRequest weAppLoginRequest, Code2SessionResponse code2SessionResponse) {
        String decryptedData = null;
        try {
            decryptedData = decrypt(weAppLoginRequest.getUserInfo().getEncryptedData(), weAppLoginRequest.getUserInfo().getIv(), code2SessionResponse.getSessionKey());
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | InvalidParameterSpecException | NoSuchAlgorithmException e) {
            throw new WeAppException(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        logger.info("decryptedData: {}", decryptedData);
        return JSON.parseObject(decryptedData, DecryptedUserInfo.class);
    }


}
