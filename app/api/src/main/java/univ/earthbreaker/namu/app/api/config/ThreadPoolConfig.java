package univ.earthbreaker.namu.core.api.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

	private static final int CORE_POOL_SIZE = 10;
	private static final int MAX_POOL_SIZE = 50;
	private static final int QUEUE_CAPACITY = 10;
	private static final String THREAD_NAME_PREFIX = "async-";

	@Bean(name = "threadPoolExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTask = new ThreadPoolTaskExecutor();
		threadPoolTask.setCorePoolSize(CORE_POOL_SIZE);
		threadPoolTask.setMaxPoolSize(MAX_POOL_SIZE);
		threadPoolTask.setQueueCapacity(QUEUE_CAPACITY);
		threadPoolTask.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTask.setThreadNamePrefix(THREAD_NAME_PREFIX);
		threadPoolTask.initialize();
		return threadPoolTask;
	}
}
