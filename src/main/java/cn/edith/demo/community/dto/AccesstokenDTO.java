package cn.edith.demo.community.dto;

/**
 * 当参数多余2个时，封装成一个对象
 */

public class AccesstokenDTO {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String state;
    private String code;

    public String getClient_id() {
        return client_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    
 }