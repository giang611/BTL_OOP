package org.thuvien.unitTest.modelsTest;
import org.thuvien.models.Member;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testConstructor() {
        String name = "Nguyen Van A";
        String mssv = "SV001";
        String phone = "0123456789";
        String pass = "password123";

        Member member = new Member(name, mssv, phone, pass);

        assertEquals(name, member.getName());
        assertEquals(mssv, member.getMssv());
        assertEquals(phone, member.getPhoneNumber());
        assertEquals(pass, member.getPassword());
    }

    @Test
    void testHandleBeforeCreate() {
        Member member = new Member();

        assertNotNull(member.getCreatedAt(), "Ngày tạo phải được khởi tạo");
    }
}