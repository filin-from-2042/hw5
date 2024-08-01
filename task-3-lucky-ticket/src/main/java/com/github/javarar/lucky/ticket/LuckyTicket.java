package com.github.javarar.lucky.ticket;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LuckyTicket {

    private static CountDownLatch countDownLatch;
    private static final int THREADS_COUNT = 7;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LuckyTicket luckyTicket = new LuckyTicket();
        AbstractMap.SimpleEntry<LuckyTicketResultDto, LuckyTicketResultDto> result = luckyTicket.luckyTicketProbability(8);

        System.out.println("Найдено московских счастливых билетов " + result.getKey().getTicketsCount() + " - вероятность выпадения " + result.getKey().getProbability() + "%");
        System.out.println("Найдно ленинградских счастливых билетов " + result.getValue().getTicketsCount() + " - вероятность выпадения " + result.getValue().getProbability() + "%");
    }

    public AbstractMap.SimpleEntry<LuckyTicketResultDto, LuckyTicketResultDto> luckyTicketProbability(int serialNumberLength) throws InterruptedException, ExecutionException {
        assert serialNumberLength % 2 == 0;
        assert serialNumberLength <= 8;

        countDownLatch = new CountDownLatch(THREADS_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);

        String maxNumberString = getMaxNumber(serialNumberLength);
        int maxNumber = Integer.parseInt(maxNumberString);

        // определение диапазонов для вычислений в соотвествии с количеством потоков и передача на обработку
        List<Future<ComputeResultDto>> tasks = new ArrayList<>();
        int countPerThread = (int) Math.ceil((double) maxNumber / THREADS_COUNT);
        for (int threadNumber = 1; threadNumber <= THREADS_COUNT; threadNumber++) {
            int top = Math.min(threadNumber * countPerThread, maxNumber);
            int bottom = (threadNumber - 1) * countPerThread + 1;
            tasks.add(executorService.submit(new ComputeTask(new RangeDto(bottom, top))));
        }

        countDownLatch.await();

        int moscowTicketsCount = 0;
        int leningradTicketsCount = 0;

        // обработка полученных результатов
        for (Future<ComputeResultDto> task : tasks) {
            ComputeResultDto result = task.get();
            moscowTicketsCount += result.getMoscowTickets().size();
            leningradTicketsCount += result.getLeningradTickets().size();
        }

        int moscowProbability = moscowTicketsCount / (maxNumber / 100);
        int leningradProbability = leningradTicketsCount / (maxNumber / 100);

        executorService.shutdown();
        return new AbstractMap.SimpleEntry<>(new LuckyTicketResultDto(moscowTicketsCount, moscowProbability), new LuckyTicketResultDto(leningradTicketsCount, leningradProbability));
    }

    /**
     * Генерация максимального числа по длине
     * @param length
     * @return - строковое представление максимального числа
     */
    private String getMaxNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < length; index++) {
            sb.append("9");
        }
        return sb.toString();
    }

    public static class ComputeTask implements Callable<ComputeResultDto> {
        private final RangeDto rangeDto;

        public ComputeTask(RangeDto rangeDto) {
            this.rangeDto = rangeDto;
        }

        @Override
        public ComputeResultDto call() {
            List<Integer> moscowTickets = new ArrayList<>();
            List<Integer> leningradTickets = new ArrayList<>();
            for (int number = rangeDto.getFrom(); number <= rangeDto.getTo(); number++) {
                Ticket ticket = new Ticket(number);
                if (ticket.isLeningradTicket()) {
                    moscowTickets.add(number);
                }
                if (ticket.isMoscowTicket()) {
                    leningradTickets.add(number);
                }
            }

            countDownLatch.countDown();
            return new ComputeResultDto(rangeDto, moscowTickets, leningradTickets);
        }
    }

    public static class LuckyTicketResultDto {
        private final int ticketsCount;
        private final int probability;

        public LuckyTicketResultDto(int tocketsCount, int probability) {
            this.ticketsCount = tocketsCount;
            this.probability = probability;
        }

        public int getTicketsCount() {
            return ticketsCount;
        }

        public int getProbability() {
            return probability;
        }
    }

}