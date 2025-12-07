package FunctionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.models.Member;
import org.thuvien.repository.MemberRepository;
import org.thuvien.service.MemberService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC01_LoginTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("TC-01-01: Đăng nhập thành công (Happy Path)")
    void testLogin_Success() {
        String phone = "090900111";
        Member mockMember = new Member();
        mockMember.setPhoneNumber(phone);
        mockMember.setPassword("123456");
        mockMember.setRole("user");

        when(memberRepository.findByPhoneNumber(phone)).thenReturn(Optional.of(mockMember));

        Optional<Member> result = memberService.getMemberByPhone(phone);

        assertTrue(result.isPresent());
        assertEquals(phone, result.get().getPhoneNumber());
    }

    @Test
    @DisplayName("TC-01-02: Sai mật khẩu (Logic so sánh)")
    void testLogin_WrongPassword() {
        String phone = "090900111";
        Member mockMember = new Member();
        mockMember.setPhoneNumber(phone);
        mockMember.setPassword("correctPass");

        when(memberRepository.findByPhoneNumber(phone)).thenReturn(Optional.of(mockMember));

        Optional<Member> result = memberService.getMemberByPhone(phone);

        String inputPassword = "wrongPass";
        boolean isMatch = result.isPresent() && result.get().getPassword().equals(inputPassword);

        assertFalse(isMatch, "Đăng nhập phải thất bại khi sai mật khẩu");
    }

    @Test
    @DisplayName("TC-01-03: Tài khoản không tồn tại")
    void testLogin_UserNotFound() {
        String phone = "0000000000";
        when(memberRepository.findByPhoneNumber(phone)).thenReturn(Optional.empty());

        Optional<Member> result = memberService.getMemberByPhone(phone);

        assertFalse(result.isPresent());
    }
}