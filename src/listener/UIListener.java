package listener;

import java.util.function.Consumer;

import dto.response.ResponseWrapper;

public class UIListener extends Listener {
    private Consumer<ResponseWrapper> callback;

    public UIListener() {
    }

    public UIListener(Consumer<ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void bindCallback(Consumer<ResponseWrapper> callback) {
        this.callback = callback;
    }

    @Override
    public <T> void invoke(T d) {
        try {
            response = (ResponseWrapper)d;
            callback.accept(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
