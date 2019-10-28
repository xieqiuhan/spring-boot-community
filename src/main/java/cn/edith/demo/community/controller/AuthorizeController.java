package cn.edith.demo.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edith.demo.community.dto.AccesstokenDTO;

import cn.edith.demo.community.dto.GitHubUserDTO;
import cn.edith.demo.community.provider.GitHubProvider;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    /**
     * 接收参数（来自url）
     * 
     * @param code
     * @param state
     * @return
     */
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
            HttpServletRequest request) {
        AccesstokenDTO accessTokenDTO = new AccesstokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUserDTO userMsg = gitHubProvider.getUser(token);
        if (userMsg != null) {
            // 登陆成功，写cookie和session
            request.getSession().setAttribute("user", userMsg);
            return "redirect:/";

        } else {
            // 登陆失败，重新登陆
            return "redirect:/";
        }
    

    }

}