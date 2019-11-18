package me.zonghua.weapp.weappdemo.response;

import org.springframework.beans.BeanUtils;

public class WeAppLoginResponse {
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String token;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public static WeAppLoginResponse fromDecryptedUserInfo(DecryptedUserInfo decryptedUserInfo) {
        WeAppLoginResponse target = new WeAppLoginResponse();
        BeanUtils.copyProperties(decryptedUserInfo, target);
        return target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
