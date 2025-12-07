package FunctionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thuvien.models.Rating;
import org.thuvien.repository.RatingRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UC06_RateBookTest {

    @Mock
    private RatingRepository ratingRepository;

    @Test
    @DisplayName("TC-06-01: Lưu đánh giá mới")
    void testSaveRating() {
        Rating rating = new Rating();
        rating.setRating(5);
        rating.setReview("Tuyệt vời");

        ratingRepository.save(rating);

        verify(ratingRepository, times(1)).save(rating);
    }
}