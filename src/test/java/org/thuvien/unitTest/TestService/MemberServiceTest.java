package org.thuvien.unitTest.TestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.models.Member;
import org.thuvien.repository.MemberRepository;
import org.thuvien.service.MemberService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void testGetMemberByPhone() {
        Member m = new Member("Nguyen Van A", "MSSV01", "0912345678", "password");
        when(memberRepository.findByPhoneNumber("0912345678")).thenReturn(Optional.of(m));

        Optional<Member> result = memberService.getMemberByPhone("0912345678");

        assertTrue(result.isPresent());
        assertEquals("Nguyen Van A", result.get().getName());
    }

    @Test
    void testGetMemberByMssv() {
        Member m = new Member("Le Thi B", "B19DCCN002", "0987654321", "password");
        when(memberRepository.findByMssv("B19DCCN002")).thenReturn(m);

        Member result = memberService.getMemberByMssv("B19DCCN002");
        assertNotNull(result);
        assertEquals("B19DCCN002", result.getMssv());
    }

    @Test
    void testCreateMember() {
        Member m = new Member();
        memberService.createMember(m);
        verify(memberRepository).save(m);
    }

    @Test
    void testFindAllUser() {
        when(memberRepository.findAllByRole("user")).thenReturn(Arrays.asList(new Member(), new Member()));
        List<Member> result = memberService.findAllUser();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteMember() {
        Member m = new Member();
        memberService.deleteMember(m);
        verify(memberRepository).delete(m);
    }
    @Test
    void testGetMemberByPhone_NotFound() {
        String strangePhone = "0000000000";
        when(memberRepository.findByPhoneNumber(strangePhone)).thenReturn(java.util.Optional.empty());

        java.util.Optional<Member> result = memberService.getMemberByPhone(strangePhone);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetMemberByMssv_ReturnNull() {
        when(memberRepository.findByMssv("UNKNOWN")).thenReturn(null);

        Member result = memberService.getMemberByMssv("UNKNOWN");

        assertNull(result);
    }
}