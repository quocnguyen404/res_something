package listener;

import dto.response.ResponseWrapper;

public class Listener {
    protected ResponseWrapper response;

    Listener() {}
    public void invoke() {}
    public <T> void invoke(T d) {}
    public <T, T1> void invoke(T d, T1 d1) {}
    public <T, T1, T2> void invoke(T d, T1 d1, T2 d2) {}
    public <T, T1, T2, T3> void invoke(T d, T1 d1, T2 d2, T3 d3) {}

    public ResponseWrapper getResponse() {
        return response;
    }

    public void clear() {
        response = null;
    }
}
