package listener;

import java.util.function.Consumer;

public class ConsumerListener<T> extends Listener {
    private Consumer<T> callback;

    public ConsumerListener() {
    }

    public ConsumerListener(Consumer<T> callback) {
        this.callback = callback;
    }

    public void bindCallback(Consumer<T> callback) {
        this.callback = callback;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> void invoke(U d) {
        try {
            callback.accept((T)d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        callback = null;
    }
    
}
