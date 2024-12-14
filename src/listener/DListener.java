package listener;

import dto.response.ResponseWrapper;

public class DListener extends Listener{
    protected ResponseWrapper response;
    DListener() {}


    @Override 
    public final ResponseWrapper getResponse() {
        return response;
    }

    @Override
    public void clear() {
        response = null;
    }
}
