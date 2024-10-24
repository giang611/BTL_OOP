package org.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuvien.models.Member;
import org.thuvien.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    public Optional<Member> getMemberByPhone(String phone) {
        Optional<Member> member = memberRepository.findByPhoneNumber(phone);
        return member;
    }
    public void createMember(Member member) {
        memberRepository.save(member);
    }
    public Optional<Member> getMemberByMssv(String mssv) {
        Optional<Member> member = memberRepository.findByMssv(mssv);
        return member;
    }
}
