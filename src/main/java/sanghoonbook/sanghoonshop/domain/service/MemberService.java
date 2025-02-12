package sanghoonbook.sanghoonshop.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanghoonbook.sanghoonshop.domain.Member;
import sanghoonbook.sanghoonshop.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Member saveMember(Member member){

        if (memberRepository.existsByName(member.getName())){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        return memberRepository.save(member);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findById(id)
                .orElse(null);

    }

    public void delete(Member member){
        memberRepository.delete(member);
    }

    public Member getMemberById(Long id){
            return memberRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID의 회원이 존재하지 않습니다."));
    }

    public Member register(String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(username, encodedPassword, role);
        return memberRepository.save(member);
    }

    public boolean login(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return passwordEncoder.matches(password, member.getPassword()); // 암호화된 비밀번호 비교
        }
        return false;
    }

}
