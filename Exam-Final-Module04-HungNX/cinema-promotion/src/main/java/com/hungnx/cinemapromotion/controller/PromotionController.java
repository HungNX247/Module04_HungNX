package com.hungnx.cinemapromotion.controller;

import com.hungnx.cinemapromotion.entity.Promotion;
import com.hungnx.cinemapromotion.service.PromotionService;
import com.hungnx.cinemapromotion.web.form.PromotionForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public String list(@RequestParam(required = false) Integer discount,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                       Model model) {
        List<Promotion> items = promotionService.search(discount,startDate,endDate);

        model.addAttribute("items",items);
        model.addAttribute("discount", discount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "promotion/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new PromotionForm());
        }
        model.addAttribute("isEdit",false);
        model.addAttribute("pageTitle","Thêm mới khuyến mãi");
        model.addAttribute("formAction", "/promotions/create");
        return "promotion/form";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("form")PromotionForm form, BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        validateDateLogic(form, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit",false);
            model.addAttribute("pageTitle","Thêm mới khuyến mãi");
            model.addAttribute("formAction", "/promotions/create");
            return "promotion/form";
        }

        promotionService.create(form);
        redirectAttributes.addFlashAttribute("successMessage","Thêm khuyến mãi thành công.");
        return "redirect:/promotions";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id,Model model) {
        model.addAttribute("form", promotionService.findFormById(id));
        model.addAttribute("isEdit",true);
        model.addAttribute("promotionId",id);
        model.addAttribute("pageTitle","Cập nhật khuyến mãi");
        model.addAttribute("formAction", "/promotions/" + id + "/edit");
        return "promotion/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("form") PromotionForm promotionForm,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        validateDateLogic(promotionForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit",true);
            model.addAttribute("promotionId",id);
            model.addAttribute("pageTitle","Cập nhật khuyến mãi");
            model.addAttribute("formAction", "/promotions/" + id + "/edit");
            return "promotion/form";
        }

        promotionService.update(id, promotionForm);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khuyến mãi thành công.");
        return "redirect:/promotions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        promotionService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage","Xóa khuyến mãi thành công.");
        return "redirect:/promotions";
    }

    private void validateDateLogic(PromotionForm form, BindingResult bindingResult) {
        if (form.getStartDate() != null && form.getEndDate() != null
                && form.getEndDate().isBefore(form.getStartDate().plusDays(1))) {
            bindingResult.rejectValue(
                    "endDate",
                    "endDate.afterStartOneDay",
                    "Thời gian kết thúc phải lớn hơn thời gian bắt đầu ít nhất 1 ngày"
            );
        }

        if (form.getStartDate() != null && !form.getStartDate().isAfter(LocalDate.now())) {
            bindingResult.rejectValue("startDate","startDate.future","Thời gian bắt đầu phải lớn hơn ngày hiện tại");
        }
    }
}
