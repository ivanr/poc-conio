import com.offbynull.coroutines.user.Continuation;

public final class MyCoroutine extends BaselineCoroutine {

    private String name;

    private Continuation c;

    public MyCoroutine(String name) {
        this.name = name;
    }

    @Override
    public void run(Continuation c) {
        this.c = c;
        System.out.println("Coroutine " + name + ": started");

        for (int i = 0; i < 10; i++) {
            System.out.println("Coroutine " + name + ": sleep " + i);
            conio.sleep(c, 100);
        }
    }
}
