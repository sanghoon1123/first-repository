package sanghoonbook.sanghoonshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sanghoonbook.sanghoonshop.config.SignupRequest;
import sanghoonbook.sanghoonshop.domain.service.MemberService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        memberService.register(request.getUsername(), request.getPassword(), request.getRole());
        return "회원가입 성공!";
    }

    @PostMapping("/login")
    public String login(@RequestBody SignupRequest request) {
        boolean isAuthenticated = memberService.login(request.getUsername(), request.getPassword());
        return isAuthenticated ? "로그인 성공" : "로그인 실패";
    }
}