package com.github.javarar.lucky.ticket;

public class Ticket {

    private final int number;

    public Ticket(int number) {
        this.number = number;
    }

    public boolean isMoscowTicket() {
        return sumNumbersByType(String.valueOf(number), SumType.EVEN) == sumNumbersByType(String.valueOf(number), SumType.ODD);
    }

    int sumNumbersByType(String number, SumType sumType) {
        int startIndex = 0;
        switch (sumType) {
            case EVEN:
                startIndex = 1;
                break;
            case ODD:
                startIndex = 0;
                break;
        }
        int sum = 0;
        for (int index = startIndex; index < number.length(); index = index + 2) {
            sum += Integer.parseInt(number.substring(index, index + 1));
        }
        return sum;
    }

    public boolean isLeningradTicket() {
        String numberString = String.valueOf(number);
        int numberCount = numberString.length() / 2;

        int startSum = sumRange(numberString, 0, numberCount);
        int endSum = sumRange(numberString, numberCount, numberString.length());

        return startSum == endSum;
    }

    int sumRange(String number, int from, int to) {
        int sum = 0;
        for (int index = from; index < to; index++) {
            sum += Integer.parseInt(number.substring(index, index + 1));
        }
        return sum;
    }

    enum SumType {
        EVEN, ODD
    }
}
