package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        final Member member = new Member("member11", 1000);
        repository.save(member);

        // find
        final Member findMember = repository.findById(member.getMemberId());
        log.info("findmember={}", findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        repository.update(member.getMemberId(), 10000);
        final Member updateMember = repository.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(10000);

        repository.delete(member.getMemberId());

        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

    }
}