import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;

public abstract class BaselineCoroutine implements Coroutine {

    protected Conio conio;

    public void setConio(Conio conio) {
        this.conio = conio;
    }
}
