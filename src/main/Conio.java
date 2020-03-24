import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;
import com.offbynull.coroutines.user.CoroutineRunner;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Conio {

    private Deque<CoroutineRunner> readyToRun = new ArrayDeque<>();

    private AtomicInteger activeCoroutines = new AtomicInteger(0);

    private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    private ThreadLocal<Continuation> threadLocal = new ThreadLocal<>();

    public void sleep(Continuation c, int millis) {
        CoroutineRunner runner = (CoroutineRunner)c.getContext();
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                runAgain(runner);
            }
        }, millis, TimeUnit.MILLISECONDS);

        activeCoroutines.getAndIncrement();
        c.suspend();
    }

    public synchronized void add(Coroutine coroutine) {
        CoroutineRunner runner = new CoroutineRunner(coroutine);
        runner.setContext(runner);
        readyToRun.add(runner);
        activeCoroutines.getAndIncrement();
        notify();
    }

    private synchronized void runAgain(CoroutineRunner runner) {
        readyToRun.add(runner);
        notify();
    }

    public void run() throws InterruptedException {
        while (activeCoroutines.get() > 0) {
            if (readyToRun.size() > 0) {
                CoroutineRunner runner;
                synchronized (this) {
                    runner = readyToRun.pop();
                }

                runner.execute();
                activeCoroutines.decrementAndGet();
            } else {
                synchronized (this) {
                    wait();
                }
            }
        }

        timer.shutdownNow();
    }

    public void assignToThread(Continuation c) {
        threadLocal.set(c);
    }
}
