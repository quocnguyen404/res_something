package listener;

import java.util.function.BiConsumer;

public class BiConsumerListener<T, T1> extends Listener {
    private BiConsumer<T, T1> callback;

    public BiConsumerListener() {
    }

    public BiConsumerListener(BiConsumer<T, T1> callback) {
        this.callback = callback;
    }

    public void bindCallback(BiConsumer<T, T1> callback) {
        this.callback = callback;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U, U1> void invoke(U d, U1 d1) {
        try {
            callback.accept((T)d, (T1)d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        callback = null;
    }
}
