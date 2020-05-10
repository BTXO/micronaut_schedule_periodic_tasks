package example.micronaut;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import javax.inject.Singleton;

public class Application implements ApplicationEventListener<ServerStartupEvent> {

    private final RegisterUseCase registerUserCase;
    
    // コンストラクタ注入
    public Application(RegisterUseCase registerUserCase) {
        this.registerUserCase = registerUserCase;
    }
    
    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
    
    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        try {
            registerUserCase.register("harry@micronaut.example");
            Thread.sleep(20000);
            registerUserCase.register("ron@micronaut.example");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}