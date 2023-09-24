package ghkwhd.oauth.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    @GetMapping("/user")
    public String user() {
        return "user 입니다";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin 입니다";
    }
}