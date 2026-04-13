package com.hungnx.cinemapromotion.service;

import com.hungnx.cinemapromotion.entity.Promotion;
import com.hungnx.cinemapromotion.repository.PromotionRepository;
import com.hungnx.cinemapromotion.web.form.PromotionForm;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {
    private final PromotionRepository promotionRepository;

    @Transactional(readOnly = true)
    public List<Promotion> search(Integer discount, LocalDate startDate, LocalDate endDate) {
        return promotionRepository.search(discount, startDate,endDate);
    }

    @Transactional(readOnly = true)
    public PromotionForm findFormById(Long id) {
        Promotion promotion = findById(id);

        PromotionForm form = new PromotionForm();
        form.setTitle(promotion.getTitle());
        form.setStartDate(promotion.getStartDate());
        form.setEndDate(promotion.getEndDate());
        form.setDiscount(promotion.getDiscount());
        form.setDetail(promotion.getDetail());
        return form;
    }



    public void create(PromotionForm promotionForm) {
        Promotion promotion = new Promotion();
        mapFormToEntity(promotionForm, promotion);
        promotionRepository.save(promotion);
    }

    public void update(Long id, PromotionForm promotionForm) {
        Promotion promotion = findById(id);
        mapFormToEntity(promotionForm, promotion);
        promotionRepository.save(promotion);
    }

    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }

    private void mapFormToEntity(PromotionForm promotionForm, Promotion promotion) {
        promotion.setTitle(promotionForm.getTitle());
        promotion.setStartDate(promotionForm.getStartDate());
        promotion.setEndDate(promotionForm.getEndDate());
        promotion.setDiscount(promotionForm.getDiscount());
        promotion.setDetail(promotionForm.getDetail());
    }

    private @NonNull Promotion findById(Long id) {
        return promotionRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Không tim thấy khuyến mãi với id = " + id));
    }
}
