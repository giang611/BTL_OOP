package org.thuvien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thuvien.models.Member;
import org.thuvien.repository.MemberRepository;

import java.util.List;
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
    public Member getMemberByMssv(String mssv) {
        Member member = memberRepository.findByMssv(mssv);
        return member;
    }
    public List<Member> findAllUser()
    {
        return memberRepository.findAllByRole("user");
    }
    public void save(Member member) {
        memberRepository.save(member);
    }
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
    public void updateMember(Member member) {
        memberRepository.save(member);
    }
}
