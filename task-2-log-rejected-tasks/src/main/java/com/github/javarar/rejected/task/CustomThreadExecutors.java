package com.github.javarar.rejected.task;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomThreadExecutors {

    public static ExecutorService logRejectedThreadPoolExecutor(int corePoolSize,
                                                         int maximumPoolSize) {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                0,
                NANOSECONDS,
                new ArrayBlockingQueue<>(1),
                (r, executor) -> {
                    ((FutureTask<?>)r).cancel(true);
                    System.out.println("Задача отклонена " + r);
                }
        );
    }
}
