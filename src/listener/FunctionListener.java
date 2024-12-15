package listener;

import java.util.function.Supplier;

public class FunctionListener<Type> extends TListener<Type> {
    private Supplier<Type> callback;

    public FunctionListener(Supplier<Type> callback) {
        this.callback = callback;
    }

    public void bindCallback(Supplier<Type> callback) {
        this.callback = callback;
    }

    @Override
    public void invoke() {
        try {
            data = callback.get();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    @Override
    public void clear() {
        callback = null;
    }
}
