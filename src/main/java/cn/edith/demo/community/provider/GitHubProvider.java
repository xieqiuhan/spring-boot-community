package cn.edith.demo.community.provider;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import cn.edith.demo.community.dto.AccesstokenDTO;
import cn.edith.demo.community.dto.GitHubUserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
/**
 * 将当前类初始化到spring的上下文（ioc）,不需要 new实例化去使用
 */
public class GitHubProvider {
    public String getAccessToken(AccesstokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public GitHubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GitHubUserDTO gitHubUser = JSON.parseObject(string, GitHubUserDTO.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
}