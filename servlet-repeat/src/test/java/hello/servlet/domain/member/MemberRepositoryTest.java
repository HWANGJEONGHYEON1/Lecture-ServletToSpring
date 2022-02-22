package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        final Member member = new Member("hello", 20);
        //when
        final Member saveMember = memberRepository.save(member);

        //then
        final Member findMember = memberRepository.findById(saveMember.getId());

        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        final Member member1 = new Member("member1", 20);
        final Member member2 = new Member("member2", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> result = memberRepository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(member1, member2);
    }

}