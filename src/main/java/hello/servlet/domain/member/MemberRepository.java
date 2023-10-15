package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * 회원 저장소는 싱글톤 패턴을 적용했다.
 * 스프링을 사용하면 스프링 빈으로 등록하면 되지만,지금은 최대한 스프링 없이 순수 서블릿 만으로 구현하는 것이 목적이다.
 * 싱글톤 패턴은 객체를 단 하나만 생생해서 공유해야 하므로 생성자를 private 접근자로 막아둔다.
 */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 다형성
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
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
        return new ArrayList<>(store.values()); // store 안의 값에 영향을 주지 않고 ArrayList 배열을 활용 가능
    }

    public void clearStore() {
        store.clear();
    }
}
