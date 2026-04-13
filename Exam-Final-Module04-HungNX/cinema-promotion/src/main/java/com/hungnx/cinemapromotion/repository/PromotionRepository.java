package com.hungnx.cinemapromotion.repository;

import com.hungnx.cinemapromotion.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("""
select  p from Promotion p\s
where (:discount is null or p.discount = :discount)\s
and (:startDate is null or p.startDate = :startDate)\s
and (:endDate is null  or p.endDate = :endDate)\s
order by p.startDate desc, p.id desc\s""")
    List<Promotion> search(@Param("discount") Integer discount,
                           @Param("startDate")LocalDate startDate,
                           @Param("endDate") LocalDate endDate);
}
