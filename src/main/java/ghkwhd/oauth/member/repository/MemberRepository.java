package ghkwhd.oauth.member.repository;

import ghkwhd.oauth.member.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MemberRepository {
    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findById(String id) {
        String jpql = "select u from Member u where u.id=:id";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class).setParameter("id", id);
        List<Member> MemberList = query.getResultList();
        if (MemberList.size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(MemberList.get(0));
    }
}
