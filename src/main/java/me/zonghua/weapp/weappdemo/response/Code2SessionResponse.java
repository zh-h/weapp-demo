package me.zonghua.weapp.weappdemo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code2SessionResponse {
    @JsonProperty(value = "openid")
    private String openId;
    @JsonProperty(value = "session_key")
    private String sessionKey;
    @JsonProperty(value = "unionId")
    private String unionId;
    @JsonProperty(value = "errcode")
    private Integer errCode;
    @JsonProperty(value = "errmsg")
    private String errMsg;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Code2SessionResponse{");
        sb.append("openId='").append(openId).append('\'');
        sb.append(", sessionKey='").append(sessionKey).append('\'');
        sb.append(", unionId='").append(unionId).append('\'');
        sb.append(", errCode='").append(errCode).append('\'');
        sb.append(", errMsg='").append(errMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
