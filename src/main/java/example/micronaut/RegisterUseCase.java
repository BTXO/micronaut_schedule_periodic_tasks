package example.micronaut;

import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Singleton
public class RegisterUseCase {
    
    private static final Logger LOG = LoggerFactory.getLogger(RegisterUseCase.class);
    
    protected final TaskScheduler taskScheduler;
    protected final EmailUseCase emailUseCase;
    
    // コンストラクタ注入 
    public RegisterUseCase(EmailUseCase emailUseCase, @Named(TaskExecutors.SCHEDULED) TaskScheduler taskScheduler) {
        this.emailUseCase = emailUseCase;
        this.taskScheduler = taskScheduler;
    }
    
    public void register(String email) {
        LOG.info("saving {} at {}", email, new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        scheduleFollowupEmail(email, "Welcome to Micronaut");
    }
    
    public void scheduleFollowupEmail(String email, String message) {
        // タスク作成
        EmailTask task = new EmailTask(emailUseCase, email, message);
        // 1分後タスク起動
        taskScheduler.schedule(Duration.ofMinutes(1), task);
    }
    
}