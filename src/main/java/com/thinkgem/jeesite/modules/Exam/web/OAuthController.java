package com.thinkgem.jeesite.modules.Exam.web;

import com.thinkgem.jeesite.common.WeChat.AdvancedUtil;
import com.thinkgem.jeesite.common.WeChat.SNSUserInfo;
import com.thinkgem.jeesite.common.WeChat.WeixinOauth2Token;
import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/Exam/OAuth")
public class OAuthController extends BaseController {

    @RequestMapping(value = {"Index", ""})
    public String Index(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxe34a90ac7bxxcab85c", "1207d566090a8344xxxd6224c02c", code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数

            model.addAttribute("snsUserInfo", snsUserInfo);
            model.addAttribute("state", state);
        }
        return "exam/index";
    }
}
