package listener;

public class TListener<Type> extends Listener {
    protected Type data;

    TListener() {}
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getData() {
        return (T)data;
    }

    @Override
    public void clear() {
    }

    @Override
    public void clearData() {
        data = null;
    }
}
