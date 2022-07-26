package com.beloved.utils;

import java.util.concurrent.*;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-25 09:54
 * @Description:
 */
public class DebounceUtils {

    private static final ScheduledExecutorService SCHEDULE = Executors.newSingleThreadScheduledExecutor();
    private static final ConcurrentHashMap<Object, Future<?>> DELAYED_MAP = new ConcurrentHashMap<>();

    public static void debounce(final Object key, final Runnable runnable, long delay, TimeUnit unit) {
        final Future<?> prev = DELAYED_MAP.put(key, SCHEDULE.schedule(() -> {
            try {
                runnable.run();
            } finally {
                DELAYED_MAP.remove(key);
            }
        }, delay, unit));
        if (prev != null) {
            prev.cancel(true);
        }
    }

    public static void shutdown() {
        SCHEDULE.shutdownNow();
    }
}
