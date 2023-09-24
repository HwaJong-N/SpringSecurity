package ghkwhd.oauth.common.controller;

import ghkwhd.oauth.member.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/signUp")
    public String loadSignUp() {
        return "member/signUp";
    }

    @GetMapping("/loginHome")
    public String loginHome() {
        return "member/loginHome";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "member/loginSuccess";
    }
}
