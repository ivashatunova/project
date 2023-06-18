package data;

import lombok.Value;

    public class DataHelper {
        private DataHelper() {
        }

        @Value
        public static class PayInfo {
            private String cardNumber;
            private String month;
            private String year;
            private String cardHolder;
            private String cvc;

        }



        public static PayInfo getApprovedCardInfo () {
            return new PayInfo("1111222233334444", "12", "26", "Mr Approved", "456");
        }

        public static PayInfo getDeclinedCardInfo () {
            return new PayInfo("5555666677778888", "06", "26", "Mr Declined", "456");
        }

        public static PayInfo getOldCardInfo () {
            return new PayInfo("1111222233334444", "12", "20", "Mr Old", "456");
        }

        public static PayInfo getInvalidCardInfo () {
            return new PayInfo("1111222233334446", "12", "26", "Mr Old", "456");
        }

}
