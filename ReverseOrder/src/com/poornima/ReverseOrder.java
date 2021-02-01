package com.poornima;

import java.math.BigInteger;
import java.util.Scanner;

public class ReverseOrder {
 public static void reverseOrder(BigInteger number){

      BigInteger reverseNumber = new BigInteger(String.valueOf(number));
      BigInteger mathNumber = new BigInteger("10");

      if(reverseNumber.compareTo(mathNumber)==-1){
          System.out.println(reverseNumber);
          return;
      }else {
          System.out.print(reverseNumber.mod(mathNumber));
          reverseNumber = reverseNumber.divide(mathNumber);
          reverseOrder(reverseNumber);
      }
  }

    public static void main(String[] args) {
        System.out.println("Enter Number to be reversed");
        Scanner scanner = new Scanner(System.in);

        BigInteger number = scanner.nextBigInteger();
        System.out.println("The reversed Number is " );
        reverseOrder(number);

    }
}
