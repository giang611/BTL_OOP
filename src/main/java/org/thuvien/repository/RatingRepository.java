package org.thuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.thuvien.models.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("SELECT r FROM Rating r ORDER BY r.createdAt DESC")
    List<Rating> findRatingsForPage(@Param("page") int page, @Param("size") int size);
}
