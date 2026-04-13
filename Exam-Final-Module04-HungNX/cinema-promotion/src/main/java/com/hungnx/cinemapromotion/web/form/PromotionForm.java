package com.hungnx.cinemapromotion.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PromotionForm {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255,message = "Tiêu đề tối đa 255 ký tự")
    private String title;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Min(value = 10001, message = "Mức giảm giá phải lớn hơn 10.000 VNĐ")
    private Integer discount;

    @NotBlank(message = "Chi tiết không được để trống")
    @Size(max = 2000, message = "Chi tiết tối đa 2000 ký tự")
    private String detail;
}
