package listener;

public class ActionListener extends Listener {
    private Runnable callback;

    public ActionListener() {
    }

    public ActionListener(Runnable callback) {
        this.callback = callback;
    }

    public void bindCallback(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void invoke() {
        try {
            callback.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        callback = null;
    }
}
