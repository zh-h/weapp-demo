package me.zonghua.weapp.weappdemo.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WeAppLoginRequest {

    @NotEmpty
    private String code;
    @NotNull
    @Valid
    private UserInfoBeanX userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfoBeanX getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBeanX userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBeanX {
        @NotNull
        private String encryptedData;
        @NotEmpty
        private String iv;
        @NotEmpty
        private String signature;
        @NotNull
        @Valid
        private UserInfoBean userInfo;
        @NotEmpty
        private String rawData;

        public String getEncryptedData() {
            return encryptedData;
        }

        public void setEncryptedData(String encryptedData) {
            this.encryptedData = encryptedData;
        }

        public String getIv() {
            return iv;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public String getRawData() {
            return rawData;
        }

        public void setRawData(String rawData) {
            this.rawData = rawData;
        }

        public static class UserInfoBean {
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
