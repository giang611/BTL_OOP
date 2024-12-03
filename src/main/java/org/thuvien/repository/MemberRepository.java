package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.models.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Optional<Member> findByPhoneNumber(String phoneNumber);
    public Optional<Member> findByMssv(String mssv);
    public Optional<Member> findByName(String name);
    public List<Member> findAllByRole(String role);

}
