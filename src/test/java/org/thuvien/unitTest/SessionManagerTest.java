package org.thuvien.unitTest;

import org.junit.jupiter.api.Test;
import org.thuvien.models.Member;
import org.thuvien.utils.SessionManager;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    @Test
    void testSessionFlow() {
        // 1. Kiểm tra trạng thái ban đầu (phải null)
        SessionManager.clearSession();
        assertNull(SessionManager.getCurrentUser());

        // 2. Giả lập đăng nhập
        Member member = new Member();
        member.setName("Admin User");
        member.setRole("admin");
        SessionManager.setCurrentUser(member);

        // 3. Kiểm tra thông tin người dùng hiện tại
        assertNotNull(SessionManager.getCurrentUser());
        assertEquals("Admin User", SessionManager.getCurrentUser().getName());

        // 4. Giả lập đăng xuất
        SessionManager.clearSession();
        assertNull(SessionManager.getCurrentUser());
    }
}