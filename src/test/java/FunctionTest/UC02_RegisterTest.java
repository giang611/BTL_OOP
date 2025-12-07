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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC02_RegisterTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("TC-02-01: Đăng ký thành công")
    void testRegister_Success() {
        Member newMember = new Member("Nguyen Van A", "SV001", "0999888777", "pass123");
        when(memberRepository.save(any(Member.class))).thenReturn(newMember);

        memberService.createMember(newMember);

        verify(memberRepository, times(1)).save(newMember);
    }

    @Test
    @DisplayName("TC-02-02: Kiểm tra trùng MSSV (Duplicate Check)")
    void testRegister_DuplicateMSSV() {
        // Arrange
        String duplicateMssv = "SV001";
        Member existingMember = new Member();
        existingMember.setName(duplicateMssv);

        when(memberRepository.findByMssv(duplicateMssv)).thenReturn(existingMember);

        // Act
        Member foundMember = memberService.getMemberByMssv(duplicateMssv);

        // Assert
        assertNotNull(foundMember, "Phải tìm thấy member nếu MSSV đã tồn tại");
        assertEquals(duplicateMssv, foundMember.getMssv());
    }
}