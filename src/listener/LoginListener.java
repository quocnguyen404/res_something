package listener;

import java.util.function.BiConsumer;

public class LoginListener {
    public BiConsumer<String,String> biConsumer;
    public void invoke(String userName, String password) {
        biConsumer.accept(userName, password);
    }
}
