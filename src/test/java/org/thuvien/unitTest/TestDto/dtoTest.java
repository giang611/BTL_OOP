package org.thuvien.unitTest.TestDto;

import org.junit.jupiter.api.Test;
import org.thuvien.dto.BookAddDTO;
import org.thuvien.dto.BookDTO;
import org.thuvien.dto.RegisterDTO;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class DTOTest {

    @Test
    void testRegisterDTO() {
        RegisterDTO dto = new RegisterDTO("Nam", "0987", "pass", "pass", "email@test.com");

        assertEquals("Nam", dto.getName());
        assertEquals("0987", dto.getPhone());
        assertEquals("email@test.com", dto.getEmail());

        // Test Setters
        dto.setName("New Name");
        assertEquals("New Name", dto.getName());
    }

    @Test
    void testBookAddDTO() {
        BookAddDTO dto = new BookAddDTO();
        dto.setTitle("Java Advanced");
        dto.setQuantity(50);
        dto.setPublishedDate(new java.sql.Date(System.currentTimeMillis()));

        assertEquals("Java Advanced", dto.getTitle());
        assertEquals(50, dto.getQuantity());
        assertNotNull(dto.getPublishedDate());
    }

    @Test
    void testBookDTO() {
        BookDTO dto = new BookDTO(1, "Title", "Author", "Desc", 10, "IT");

        assertEquals(1, dto.getId());
        assertEquals("Title", dto.getName());
        assertEquals("IT", dto.getCategories());
    }
}