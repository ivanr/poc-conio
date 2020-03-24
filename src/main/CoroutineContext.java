import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.CoroutineRunner;

public class CoroutineContext {

    private CoroutineRunner runner;

    public CoroutineContext(CoroutineRunner runner) {
        this.runner = runner;
    }

    public CoroutineRunner getRunner() {
        return runner;
    }
}
