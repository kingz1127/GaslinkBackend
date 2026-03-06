package com.gaslink.api.modules.admin.dto;
import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrdersChartDto {
    private List<ChartPoint> points;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ChartPoint {
        private String date;
        private long count;
    }
}