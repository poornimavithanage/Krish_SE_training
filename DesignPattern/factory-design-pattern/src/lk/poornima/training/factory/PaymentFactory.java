package lk.poornima.training.factory;

public class PaymentFactory {
    public static Payment makePayment(PaymentCode paymentCode){
        switch (paymentCode){
            case CASH:
                return new CashPayment();
            case WIRE_TRANSFER:
                return new WireTransferPayment();
            case VISA:
                return new VisaPayment();
            default:
                return null;
        }
    }
}
