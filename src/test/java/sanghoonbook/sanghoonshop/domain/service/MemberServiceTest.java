package sanghoonbook.sanghoonshop.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sanghoonbook.sanghoonshop.domain.Address;
import sanghoonbook.sanghoonshop.domain.Member;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    void saveMember(){
        Member member = new Member("상훈", new Address("서울", "은천로 15길 24", "604동 1104호"));
        Member saveMember = memberService.saveMember(member);

        assertThat(member).isEqualTo(saveMember);
    }

    @Test
    void equalsMember(){
        Member member1 = new Member("상훈", new Address("서울", "은천로 15길 24", "604동 1104호"));
        Member member2 = new Member("상훈", new Address("부산", "부산로 15길 24", "602동 1104호"));
        Member saveMember1 = memberService.saveMember(member1);


        assertThrows(IllegalStateException.class, () -> memberService.saveMember(member2),
                "이미 존재하는 회원입니다.");

    }

    @Test
    void findAll(){
        Member member1 = new Member("상훈", new Address("서울", "은천로 15길 24", "604동 1104호"));
        Member member2 = new Member("준혁", new Address("부산", "부산로 15길 24", "602동 1104호"));
        memberService.saveMember(member1);
        memberService.saveMember(member2);
        List<Member> all = memberService.findAll();

        all.forEach(member -> log.info("member = {}", member));

    }

    @Test
    void findOne(){
        Member member1 = new Member("상훈", new Address("서울", "은천로 15길 24", "604동 1104호"));

        memberService.saveMember(member1);
        Member findMember = memberService.findOne(member1.getId());

        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    void delete(){
        Member member1 = new Member("상훈", new Address("서울", "은천로 15길 24", "604동 1104호"));

        memberService.saveMember(member1);

        memberService.delete(member1);

        Member deletedMember = memberService.findOne(member1.getId());
        assertThat(deletedMember).isNull();

    }

    @Test
    void memberFail(){
        Member member1 = new Member("영현", new Address("서울", "은천로 15길 24", "604동 1104호"));
        Member member2 = new Member("김준", new Address("부산", "부산로 15길 24", "602동 1104호"));
        memberService.saveMember(member1);
        memberService.saveMember(member2);

        assertThrows(NoSuchElementException.class, () -> memberService.getMemberById(99L),
                "해당 ID의 회원이 존재하지 않습니다.");
    }
}