package converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static String anyBaseToDecimal (int source, String fraction) {
        char[] charArray = fraction.toCharArray();
        int[] intArray = new int[charArray.length];
        double decimalValue = 0;

        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = Character.getNumericValue(charArray[i]);
        }

        for (int i = 0; i < fraction.length(); i++) {
            decimalValue = (intArray[i] / Math.pow(source,i+1) ) + decimalValue;
        }
        return Double.toString(decimalValue);
    }

    public static String decimalToAnyBase (int target, String fraction) throws ParseException {

        StringBuilder anyBase = new StringBuilder();
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        Number number = format.parse(fraction);
        double tempDecimal = number.doubleValue();

        for (int i = 0; i < 5; i++) {
            double tempValue = tempDecimal * target;
            StringBuilder tempStringBuilder = new StringBuilder(String.valueOf(tempValue));
            int dot = tempStringBuilder.indexOf(".");
            String integer = tempStringBuilder.substring(0,dot);
            integer = Integer.toString(Integer.parseInt(integer), target);
            anyBase.append(integer);
            String ffraction = tempStringBuilder.substring(dot+1,tempStringBuilder.length());
            ffraction = "0." + ffraction;
            number = format.parse(ffraction);
            tempDecimal = number.doubleValue();
        }

        return anyBase.toString();
    }

    public static String integerConverter (int source,String number, int target) {
        if (source == 1) {
            int tempDecimal = String.valueOf(number).length();
            return Integer.toString(tempDecimal, target);
        }
        else if (target == 1) {
            int tempDecimal = Integer.parseInt(number, source);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < tempDecimal; i++) {
                stringBuilder.append(1);
            }
            return stringBuilder.toString();
        }
        else {
            int tempDecimal = Integer.parseInt(number,source);
            return Integer.toString(tempDecimal, target);
        }
    }

    public static void main(String[] args) throws ParseException {

        try {
            Scanner scanner = new Scanner(System.in);
            int source = scanner.nextInt();
            String number = scanner.next();
            int target = scanner.nextInt();

            if (source < 1 || source > 36 || target < 1 || target > 36) {
                throw new IllegalArgumentException();
            }

            String answer = null;

            StringBuilder stringBuilder = new StringBuilder(number);
            int dot = stringBuilder.indexOf(".");

            if (dot == -1) {
                answer = integerConverter(source,number,target);
            }
            else {

                String integer = stringBuilder.substring(0, dot);
                String fraction = stringBuilder.substring(dot + 1, stringBuilder.length());

                String integerAnswer = integerConverter(source,integer,target);
                String temporaryFractionDecimal = anyBaseToDecimal(source,fraction);
                String fractionAnswer = decimalToAnyBase(target,temporaryFractionDecimal);
                answer = integerAnswer + "." + fractionAnswer;

            }

            System.out.println(answer);
        } catch (Exception e) {
            System.out.println("error");
        }


    }

}
