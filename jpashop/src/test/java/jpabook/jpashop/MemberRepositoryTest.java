package jpabook.jpashop;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional//테스트에서 트랜잭션은 모두 rollback 된다.
    @Rollback(value = false)
    public void testMember() throws Exception {
        //GIVEN
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //THEN
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member);//true
        /*
            같은 트랜잭션 안에서 저장,조회 등이 일어나면 영속성 컨텍스트가 똑같기 때문에
            같은 영속성 컨텍스트 안에서는 id값(식별자)이 같으면 같은 엔티티로 식별한다.
            1차 캐쉬 과정에 있는 같은 식별자를 가진 엔티티로 식별함.
        */
    }
}