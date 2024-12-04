package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thuvien.models.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
     Optional<Member> findByPhoneNumber(String phoneNumber);
     Member findByMssv(String mssv);
     Optional<Member> findByName(String name);
     List<Member> findAllByRole(String role);

}
