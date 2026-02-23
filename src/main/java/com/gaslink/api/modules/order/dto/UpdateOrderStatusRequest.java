package com.gaslink.api.modules.order.dto;
import com.gaslink.api.shared.enums.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    @NotNull private OrderStatus status;
    private String note;
}