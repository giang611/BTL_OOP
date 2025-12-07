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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC07_PersonalInfoTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("TC-07-01: Cập nhật thông tin cá nhân")
    void testUpdateInfo() {
        Member member = new Member();
        member.setId(1);
        member.setName("Old Name");

        member.setName("New Name");
        memberService.updateMember(member);

        verify(memberRepository, times(1)).save(member);
    }

    @Test
    @DisplayName("TC-07-02: Lấy thông tin theo MSSV")
    void testGetMemberByMssv() {
        String mssv = "20201234";
        Member member = new Member();
        member.setMssv(mssv);

        when(memberRepository.findByMssv(mssv)).thenReturn(member);

        Member result = memberService.getMemberByMssv(mssv);

        assertNotNull(result);
        assertEquals(mssv, result.getMssv());
    }
}