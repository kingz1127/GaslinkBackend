package com.gaslink.api.modules.admin.dto;

import java.math.BigDecimal;

public class DashboardStatsDto {
    private long totalUsers;
    private long activeVendors;
    private long ordersToday;
    private BigDecimal revenueToday;
    private long pendingVerifications;
    private int openComplaints;

    // 1. MANUAL ALL-ARGS CONSTRUCTOR (This fixes your error)
    public DashboardStatsDto(long totalUsers, long activeVendors, long ordersToday,
                             BigDecimal revenueToday, long pendingVerifications, int openComplaints) {
        this.totalUsers = totalUsers;
        this.activeVendors = activeVendors;
        this.ordersToday = ordersToday;
        this.revenueToday = revenueToday;
        this.pendingVerifications = pendingVerifications;
        this.openComplaints = openComplaints;
    }

    // 2. MANUAL NO-ARGS CONSTRUCTOR (Required for JSON serialization)
    public DashboardStatsDto() {
    }

    // 3. MANUAL GETTERS (Required because Lombok @Data is failing)
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getActiveVendors() { return activeVendors; }
    public void setActiveVendors(long activeVendors) { this.activeVendors = activeVendors; }

    public long getOrdersToday() { return ordersToday; }
    public void setOrdersToday(long ordersToday) { this.ordersToday = ordersToday; }

    public BigDecimal getRevenueToday() { return revenueToday; }
    public void setRevenueToday(BigDecimal revenueToday) { this.revenueToday = revenueToday; }

    public long getPendingVerifications() { return pendingVerifications; }
    public void setPendingVerifications(long pendingVerifications) { this.pendingVerifications = pendingVerifications; }

    public int getOpenComplaints() { return openComplaints; }
    public void setOpenComplaints(int openComplaints) { this.openComplaints = openComplaints; }
}