package listener;

import dto.response.ResponseWrapper;

import java.util.function.Supplier;

public class BaseListener extends Listener{
    private Supplier<ResponseWrapper> callback;

    public BaseListener() {
    }

    public BaseListener(Supplier<ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void bindCallback(Supplier<ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void invoke() {
        try {
            response = callback.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
