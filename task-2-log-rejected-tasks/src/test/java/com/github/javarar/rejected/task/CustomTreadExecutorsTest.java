package com.github.javarar.rejected.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class CustomTreadExecutorsTest {

    @Test
    public void threadPoolDoesNotThrowExceptionOnQueueOverflow() {
        ExecutorService executor = CustomThreadExecutors.logRejectedThreadPoolExecutor(1, 1);

        Future<?> future = executor.submit(new Task("1"));
        Future<?> future2 = executor.submit(new Task("2"));
        Future<?> future3 = executor.submit(new Task("3"));

        Assertions.assertTrue(future3.isCancelled());
    }

    static class Task implements Runnable {

        final String id;

        public Task(String id) {
            this.id = id;
        }

        @Override
        public void run() {

            try {
                System.out.println("Запуск " + id);
                sleep(1_000);
                System.out.println("Завершение " + id);
            } catch (InterruptedException e) {
                System.out.println("Ошибка");
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task " + id;
        }
    }
}
