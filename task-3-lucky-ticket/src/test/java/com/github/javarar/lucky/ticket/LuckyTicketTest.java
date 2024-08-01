package com.github.javarar.lucky.ticket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.AbstractMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LuckyTicketTest {

    @DisplayName("Задание 7. Счастливый билет")
    @ParameterizedTest
    @MethodSource("cases")
    public void luckyTicketProbabilityTest(int serialNumberLength, double probability) throws ExecutionException, InterruptedException {
        LuckyTicket ticket = new LuckyTicket();

        AbstractMap.SimpleEntry<LuckyTicket.LuckyTicketResultDto, LuckyTicket.LuckyTicketResultDto> result = ticket.luckyTicketProbability(serialNumberLength);

        assertEquals(result.getKey().getProbability(), probability);
        assertEquals(result.getValue().getProbability(), probability);
    }

    private static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(4, 6),
                Arguments.of(6, 5),
                Arguments.of(8, 4)
        );
    }

    @Test
    public void should_SumRangeCorrectly() {
        Ticket ticket = new Ticket(111111);

        int sum = ticket.sumRange("111111", 0, 3);

        assertEquals(3,sum);
    }

    @ParameterizedTest
    @ValueSource(ints = {123411, 999999, 567765, 913733})
    public void should_DetectLeningradTicket(int number) {
        Ticket ticket = new Ticket(number);

        boolean isLeningradTicket= ticket.isLeningradTicket();

        assertTrue(isLeningradTicket);
    }

    @Test
    public void when_NumberTypeEven_then_SumOnlyEvenNumbers() {
        Ticket ticket = new Ticket(123411);

        int sum = ticket.sumNumbersByType("123411", Ticket.SumType.EVEN);

        assertEquals(7,sum);
    }

    @Test
    public void when_NumberTypeOdd_then_SumOnlyOddNumbers() {
        Ticket ticket = new Ticket(123411);

        int sum = ticket.sumNumbersByType("123411", Ticket.SumType.ODD);

        assertEquals(5,sum);
    }


    @ParameterizedTest
    @ValueSource(ints = {123310, 999999, 567765, 913737})
    public void should_DetectMoscowTicket(int number) {
        Ticket ticket = new Ticket(number);

        boolean isMoscowTicket = ticket.isMoscowTicket();

        assertTrue(isMoscowTicket);
    }

}