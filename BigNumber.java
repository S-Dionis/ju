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

    private int charAt(int i) {
        return Character.getNumericValue(value.charAt(i));
    }

    public BigNumber plus(BigNumber other) {
        BigNumber it = this;

        if (it.length < other.length) {
            it = other;
            other = this;
        } else if (length == other.length) {
            for (int i = 0; i < length; i++) {
                if (other.charAt(i) > it.charAt(i)) {
                    it = other;
                    other = this;
                }
            }
        }

        return plus(it, other);
    }

    private BigNumber plus(BigNumber it, BigNumber other) {
        int maxLen = it.length + 1;
        int[] result = new int[maxLen];
        int itOffset = maxLen - it.length;
        int otherOffset = maxLen - other.length;
        int addition = 0;

        for (int i = maxLen - 1; i >= 0; i--) {
            int iIt = i - itOffset;
            int iOther = i - otherOffset;
            int sum = 0;
            int absThisNum = (iIt < 0 ? 0 : it.charAt(iIt)) + addition;
            int absOtherNum = iOther < 0 ? 0 : other.charAt(iOther);

            int thisNum = it.isNegative ? -absThisNum : absThisNum;
            int otherNum = other.isNegative ? -absOtherNum : absOtherNum;

            if (absThisNum < absOtherNum && (other.isNegative ^ it.isNegative)) {
                thisNum += 10;
                addition = -1;
                sum += thisNum + otherNum;
            } else {
                sum += thisNum + otherNum;
                addition = Math.abs(sum / 10);
            }

            sum = sum % 10;
            result[i] = sum;
        }

        int firstNonZeroInt = -1;
        for (int i = 0; i < maxLen; i++) {
            if (result[i] != 0) {
                firstNonZeroInt = i;
                break;
            }
        }

        int[] ints = new int[maxLen - firstNonZeroInt];
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
        tests();
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
