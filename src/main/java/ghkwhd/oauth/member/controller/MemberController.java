package ghkwhd.oauth.member.controller;

import ghkwhd.oauth.member.domain.MemberDTO;
import ghkwhd.oauth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String loadSignUp() {
        return "member/signUp";
    }

    @PostMapping("/signUp")
    @ResponseBody
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put("error-" + fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMap);
        }

        return memberService.findById(memberDTO.getId()).isPresent()
                ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다")
                : ResponseEntity.ok(memberService.save(memberDTO));
    }
}
