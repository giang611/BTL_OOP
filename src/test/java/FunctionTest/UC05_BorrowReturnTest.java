package FunctionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.thuvien.models.Book;

import static org.junit.jupiter.api.Assertions.*;

public class UC05_BorrowReturnTest {

    @Test
    @DisplayName("TC-05-01: Mượn thành công (Số lượng kho > mượn)")
    void testBorrow_Normal() {
        Book book = new Book();
        book.setQuantity(10);
        int borrowQty = 2;
        book.setQuantity(book.getQuantity() - borrowQty);
        assertEquals(8, book.getQuantity());
    }

    @Test
    @DisplayName("TC-05-02: Mượn trường hợp biên (Mượn hết sách)")
    void testBorrow_Boundary_ExactAmount() {
        Book book = new Book();
        book.setQuantity(5);
        int borrowQty = 5;

        boolean canBorrow = book.getQuantity() >= borrowQty;
        if(canBorrow) book.setQuantity(book.getQuantity() - borrowQty);

        assertTrue(canBorrow);
        assertEquals(0, book.getQuantity(), "Kho phải về 0");
    }

    @Test
    @DisplayName("TC-05-03: Không đủ sách (Mượn > Kho)")
    void testBorrow_NotEnough() {
        Book book = new Book();
        book.setQuantity(3);
        int borrowQty = 5;

        boolean canBorrow = book.getQuantity() >= borrowQty;

        assertFalse(canBorrow, "Không được phép mượn");
        assertEquals(3, book.getQuantity(), "Số lượng kho phải giữ nguyên");
    }

    @Test
    @DisplayName("TC-05-05: Trả sách (Cộng dồn số lượng)")
    void testReturnBook() {
        Book book = new Book();
        book.setQuantity(0); // Kho đang hết
        int returnQty = 5;

        book.setQuantity(book.getQuantity() + returnQty);
        assertEquals(5, book.getQuantity());
    }
}