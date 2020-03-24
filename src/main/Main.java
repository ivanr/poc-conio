
public class Main {

    public static void main(String[] args) throws Exception {
        Conio conio = new Conio();

        MyCoroutine coroutineA = new MyCoroutine("A");
        coroutineA.setConio(conio);

        MyCoroutine coroutineB = new MyCoroutine("B");
        coroutineB.setConio(conio);

        conio.add(coroutineA);
        conio.add(coroutineB);
        conio.run();
    }
}
