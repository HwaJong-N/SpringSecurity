package ghkwhd.oauth.member.controller;

import ghkwhd.oauth.member.domain.MemberDTO;
import ghkwhd.oauth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

        if (memberService.findById(memberDTO.getId()).isPresent()) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다");
        } else if (memberService.findByEmail(memberDTO.getEmail()).isPresent()) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다");
        }

        return ResponseEntity.ok(memberService.save(memberDTO));
    }
}
