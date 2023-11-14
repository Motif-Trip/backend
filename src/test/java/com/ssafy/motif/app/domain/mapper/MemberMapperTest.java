package com.ssafy.motif.app.domain.mapper;


import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.motif.app.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void findMember() {
        // when
        Member findMember = memberMapper.findMember(1L);

        // then
        assertThat(findMember.getNickname()).isEqualTo("ADMIN");
        System.out.println(findMember.toString());
    }
}