package sanghoonbook.sanghoonshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanghoonbook.sanghoonshop.domain.Member;
import sanghoonbook.sanghoonshop.domain.service.MemberService;
import sanghoonbook.sanghoonshop.dto.MemberDto;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberControllerApi {
    private final MemberService memberService;

    @GetMapping("{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id){
        Member findMember = memberService.getMemberById(id);
        MemberDto memberDto = new MemberDto(findMember);
        return ResponseEntity.ok(memberDto);
    }
}
