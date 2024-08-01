package com.github.javarar.lucky.ticket;

import java.util.List;

public class ComputeResultDto {
    private final RangeDto rangeDto;
    private final List<Integer> moscowTickets;
    private final List<Integer> leningradTickets;

    public ComputeResultDto(RangeDto rangeDto, List<Integer> moscowTickets, List<Integer> leningradTickets) {
        this.rangeDto = rangeDto;
        this.moscowTickets = moscowTickets;
        this.leningradTickets = leningradTickets;
    }

    public RangeDto getRangeDto() {
        return rangeDto;
    }

    public List<Integer> getMoscowTickets() {
        return moscowTickets;
    }

    public List<Integer> getLeningradTickets() {
        return leningradTickets;
    }
}
