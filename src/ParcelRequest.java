public class ParcelRequest {
    String parcelId;
    String originZone;
    String deliveryZone;
    String customerName;
    int deadline;
    int orderTime;
    int orderHistoryCount;
    boolean sameDayDelivery;
    boolean isVIP;
    int startTime;

    public ParcelRequest(int startTime, String parcelId, String originZone, String deliveryZone,
                         String customerName,int orderTime,
                         int orderHistoryCount, boolean sameDayDelivery,
                         boolean isVIP) {
        this.parcelId = parcelId;
        this.originZone = originZone;
        this.deliveryZone = deliveryZone;
        this.customerName = customerName;
        this.orderTime = orderTime;
        this.orderHistoryCount = orderHistoryCount;
        this.sameDayDelivery = sameDayDelivery;
        this.isVIP = isVIP;
        this.deadline = calculateDeadline(orderTime);
        this.startTime = startTime;

    }

    private int calculateDeadline(int orderTime) {
        if (isEffectiveSameDay()) {
            return orderTime + 720; // 12-hour (720 min) deadline for VIP/same-day
        }
        return orderTime + 2880; // 48-hour (2880 min) deadline for standard orders
    }

    // Returns true if this should be treated as same-day delivery
    public boolean isEffectiveSameDay() {
        return isVIP || sameDayDelivery;  // VIPs are automatically same-day
    }


    public String formatAbsoluteTime(int absoluteTime) {
        // Base date: 01/01/2024
        int baseDay = 1;
        int baseMonth = 1;
        int baseYear = 2024;

        // Calculate total days, hours, and minutes
        int totalDays = absoluteTime / (24 * 60);
        int remainingMinutes = absoluteTime % (24 * 60);
        int hours = remainingMinutes / 60;
        int minutes = remainingMinutes % 60;

        // Calculate final day, month, year (basic calculation assuming 30 days per month)
        int day = baseDay + totalDays;
        int month = baseMonth;
        int year = baseYear;

        while (day > 30) { // Assuming 30 days per month for simplicity
            day -= 30;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }

        // Format hour to 12-hour format with AM/PM
        String ampm = "AM";
        if (hours >= 12) {
            ampm = "PM";
            if (hours > 12) {
                hours -= 12;
            }
        }
        if (hours == 0) {
            hours = 12; // Midnight case
        }
        String formattedMinutes = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);

        // Build the final formatted string
        return String.format("%02d/%02d/%d : %d.%s%s", day, month, year, hours, formattedMinutes, ampm);
    }

    public String getOrderTimeFormatted() {
        return formatAbsoluteTime(orderTime);
    }
    public String getDeadlineFormatted() {
        return formatAbsoluteTime(deadline);
    }




}

