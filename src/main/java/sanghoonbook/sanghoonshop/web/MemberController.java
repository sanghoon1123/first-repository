package sanghoonbook.sanghoonshop.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sanghoonbook.sanghoonshop.domain.Address;
import sanghoonbook.sanghoonshop.domain.Member;
import sanghoonbook.sanghoonshop.domain.service.MemberService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        if (form.getCity() == null || form.getStreet() == null || form.getZipcode() == null) {
            result.reject("address", "주소 정보가 입력되지 않았습니다.");
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member(form.getName(), address);
        memberService.saveMember(member);

        List<Member> members = memberService.findAll();
        for (Member member1 : members) {
            System.out.println("회원 이름: " + member.getName() + ", 주소: " + member.getAddress());
        }

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model){
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
