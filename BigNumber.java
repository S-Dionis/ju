package com.company;

public class BigNumber {

    public final String value;
    private final Boolean isNegative;

    public BigNumber(String number) {
        if (number.isBlank()) {
            throw new ArithmeticException("Number is empty");
        }

        if (!number.matches("-?\\d+")) {
            throw new ArithmeticException("Number contains invalid character");
        }

        if (number.startsWith("-")) {
            this.isNegative = true;
            value = number.substring(1);
        } else {
            this.isNegative = false;
            this.value = number;
        }
    }

    public BigNumber add(BigNumber other) {
        int max = Math.max(value.length(), other.value.length());
        int[] result = new int[max + 1];
        int addition = 0;

        for (int i = max - 1; i >= 0; i--) {
            int sum = addition;
            int thisNum = Character.getNumericValue(value.length() > i ? value.charAt(i) : 0);
            int otherNum = Character.getNumericValue(other.value.length() > i ? other.value.charAt(i) : 0);

            thisNum = isNegative ? -thisNum : thisNum;
            otherNum = other.isNegative ? -otherNum : otherNum;
            sum += thisNum + otherNum;
            addition = sum / 10;
            sum = sum % 10;
            result[i + 1] = sum;
        }

        if (addition != 0) result[0] += addition;

        int firstNonZeroInt = -1;
        for (int i = 0; i < max; i++) {
            if (result[i] != 0) {
                firstNonZeroInt = i;
                break;
            }
        }

        int[] ints = new int[max + 1 - firstNonZeroInt];
        System.arraycopy(result, firstNonZeroInt, ints, 0, ints.length);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ints.length; i++) {
            if (i != 0) {
                sb.append(Math.abs(ints[i]));
            } else {
                sb.append(ints[i]);
            }
        }

        return new BigNumber(sb.toString());
    }

    @Override
    public String toString() {
        return isNegative ? "-" + value : value;
    }

    public static void main(String[] args) {
        BigNumber bigNumberA = new BigNumber("-77777");
        BigNumber bigNumberB = new BigNumber("-44444");

        BigNumber add = bigNumberA.add(bigNumberB);
        System.out.println();
    }

}
