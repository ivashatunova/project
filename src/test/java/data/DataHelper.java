package data;

import lombok.Value;

import java.time.LocalDate;

public class DataHelper {
    private DataHelper() {
    }

    public final static String APPROVED_STATUS = "APPROVED";
    public final static String DECLINED_STATUS = "DECLINED";

    @Value
    public static class PayInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvc;

    }

    private final static String datePattern = "dd.MM.yyyy";

    public static String getYear() {
        int year = LocalDate.now().plusYears(3).getYear();
        String stringYear = String.valueOf(year).substring(2);
        return stringYear;
    }

    public static String getYearOld() {
        int year = LocalDate.now().minusYears(2).getYear();
        String stringYear = String.valueOf(year).substring(2);
        return stringYear;
    }



    public static PayInfo getApprovedCardInfo() {
        return new PayInfo("1111222233334444", "06", getYear(), "Mr Approved", "456");
    }

    public static PayInfo getDeclinedCardInfo() {
        return new PayInfo("5555666677778888", "06", getYear(), "Mr Declined", "456");
    }

    public static PayInfo getOldCardInfo() {
        return new PayInfo("1111222233334444", "06", getYearOld(), "Mr Old", "456");
    }

    public static PayInfo getInvalidCardInfo() {
        return new PayInfo("1111222233334446", "06", getYear(),  "Mr Invalid", "456");
    }

}
