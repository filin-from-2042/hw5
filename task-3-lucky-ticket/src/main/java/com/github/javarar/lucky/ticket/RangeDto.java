package com.github.javarar.lucky.ticket;

public class RangeDto {
    private final int from;
    private final int to;

    public RangeDto(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
