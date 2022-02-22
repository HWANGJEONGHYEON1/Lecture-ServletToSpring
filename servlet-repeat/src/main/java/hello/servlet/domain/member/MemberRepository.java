package hello.servlet.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {

    /**
     * 동시성 문제를 고려할 때는 ConcurrentHashMap, AtomicLong을 고려해야한다.
     */
    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    private MemberRepository(){}

    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
