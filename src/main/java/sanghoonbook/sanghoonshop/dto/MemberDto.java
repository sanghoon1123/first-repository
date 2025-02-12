package sanghoonbook.sanghoonshop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import sanghoonbook.sanghoonshop.domain.Address;
import sanghoonbook.sanghoonshop.domain.Member;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private Address address;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.address = member.getAddress();
    }
}
