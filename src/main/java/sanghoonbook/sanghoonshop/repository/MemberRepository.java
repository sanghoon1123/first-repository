package sanghoonbook.sanghoonshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sanghoonbook.sanghoonshop.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByName(String name);
    Optional<Member> findByUsername(String username);
}
