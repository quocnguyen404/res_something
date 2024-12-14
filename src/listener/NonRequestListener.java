package listener;

import dto.response.ResponseWrapper;

import java.util.function.Supplier;

public class NonRequestListener extends DListener{
    private Supplier<ResponseWrapper> callback;

    public NonRequestListener() {
    }

    public NonRequestListener(Supplier<ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void bindCallback(Supplier<ResponseWrapper> callback) {
        this.callback = callback;
    }

    @Override
    public void invoke() {
        try {
            response = callback.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
