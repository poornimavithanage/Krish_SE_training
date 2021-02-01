package lk.poornima.training.factory;

public class AppInitializer {
    public static void main(String[] args) {

        Payment payment = PaymentFactory.makePayment(PaymentCode.CASH);
        System.out.println(payment);

        Payment payment1 = PaymentFactory.makePayment(PaymentCode.VISA);
        System.out.println(payment1);

        Payment payment2 = PaymentFactory.makePayment(PaymentCode.WIRE_TRANSFER);
        System.out.println(payment2);


    }
}
