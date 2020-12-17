package com.virtusa;

import java.math.BigInteger;
import java.util.Scanner;

public class ReverseOrder {
 public static void reverse(BigInteger number){

      BigInteger reverseNumber = new BigInteger(String.valueOf(number));
      BigInteger calculationNumber = new BigInteger("10");

      if(reverseNumber.compareTo(calculationNumber)==-1){
          System.out.println(reverseNumber);
          return;
      }else {
          System.out.print(reverseNumber.mod(calculationNumber));
          reverseNumber = reverseNumber.divide(calculationNumber);
          reverse(reverseNumber);
      }
  }

    public static void main(String[] args) {
        System.out.println("Enter Number to be reversed");
        Scanner scanner = new Scanner(System.in);

        BigInteger number = scanner.nextBigInteger();
        System.out.println("The reversed Number is " );
        reverse(number);

    }
}
