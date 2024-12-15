package listener;

import dto.response.ResponseWrapper;

import java.util.function.Function;

public class ServiceListener<Type> extends TListener<ResponseWrapper>{
    
    private Function<Type, ResponseWrapper> callback;

    public ServiceListener() {
    }

    public ServiceListener(Function<Type, ResponseWrapper> callback) {
        this.callback = callback;
    }

    public void bindCallback(Function<Type, ResponseWrapper> callback) {
        this.callback = callback;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void invoke(T d) {
        try {
            data = callback.apply((Type)d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        callback = null;
    }
}
