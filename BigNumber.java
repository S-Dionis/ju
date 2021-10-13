package com.company;

public class BigNumber implements Comparable {

    public final String value;
    private final Boolean isNegative;
    private final int length;

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

        this.length = value.length();
    }

    public BigNumber plus(BigNumber other) {
        int max = Math.max(length, other.length);
        int maxLen = max + 1;
        int[] result = new int[maxLen];
        int addition = 0;

        int itOffset = maxLen - length;
        int otherOffset = maxLen - other.length;

        for (int i = maxLen - 1; i >= 0; i--) {

            int iIt = i - itOffset;
            int iOther = i - otherOffset;

            int sum = addition;
            int thisNum = iIt < 0 ? 0 : Character.getNumericValue(value.charAt(iIt));
            int otherNum = iOther < 0 ? 0 : Character.getNumericValue(other.value.charAt(iOther));

            thisNum = isNegative ? -thisNum : thisNum;
            otherNum = other.isNegative ? -otherNum : otherNum;

            sum += thisNum + otherNum;

            addition = sum / 10;
            sum = sum % 10;
            result[i] = sum;
        }

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


    private static BigNumber bigNumberA;
    private static BigNumber bigNumberB;
    private static BigNumber actual;
    private static BigNumber expected;

    public static void main(String[] args) throws Exception {
        singleTest();
        //tests();
    }

    private static final void singleTest() {

        bigNumberA = new BigNumber("100");
        bigNumberB = new BigNumber("-97");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(100 + -97));
        assertEquals(actual.toString(), expected.toString());
    }

    private static final void tests() {

        bigNumberA = new BigNumber("-21");
        bigNumberB = new BigNumber("-9");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(-21 + -9));
        assertEquals(actual.toString(), expected.toString());

        bigNumberA = new BigNumber("12");
        bigNumberB = new BigNumber("39");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(12 + 39));
        assertEquals(actual.toString(), expected.toString());

        bigNumberA = new BigNumber("-7");
        bigNumberB = new BigNumber("27");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(-7 + 27));
        assertEquals(actual.toString(), expected.toString());

        bigNumberA = new BigNumber("34");
        bigNumberB = new BigNumber("-999");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(34 + -999));
        assertEquals(actual.toString(), expected.toString());


        bigNumberA = new BigNumber("100");
        bigNumberB = new BigNumber("-97");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(100 + -97));
        assertEquals(actual.toString(), expected.toString());


        bigNumberA = new BigNumber("0");
        bigNumberB = new BigNumber("100009");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(0 + 100009));
        assertEquals(actual.toString(), expected.toString());


        bigNumberA = new BigNumber("-0");
        bigNumberB = new BigNumber("111");
        actual = bigNumberA.plus(bigNumberB);
        expected = new BigNumber(String.valueOf(-0 + 111));
        assertEquals(actual.toString(), expected.toString());
    }

    private static <T extends Comparable<T>> void assertEquals(T a, T b) {
        if (a.compareTo(b) != 0) {
            System.out.println(("Assertion FAILED"));
            System.out.println(a + " not equals " + b);
            System.out.println();
        } else {
            System.out.println(("Assertion PASS"));
            System.out.println(a + " equals " + b);
            System.out.println();
        }

    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        BigNumber bigNumber = o instanceof BigNumber ? ((BigNumber) o) : null;
        if (bigNumber == null) return -1;

        return this.value.compareTo(bigNumber.value);
    }
}
