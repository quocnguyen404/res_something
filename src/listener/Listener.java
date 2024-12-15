package listener;

public class Listener {
    Listener() {}
    public void invoke() {}
    public <T> void invoke(T d) {}
    public <T, T1> void invoke(T d, T1 d1) {}
    public <T, T1, T2> void invoke(T d, T1 d1, T2 d2) {}

    // public ResponseWrapper getResponse() {
    //     return null;
    // }

    public <T> T getData() {
        return null;
    }

    public void clear() {
    }

    public void clearData() {
    }
}
