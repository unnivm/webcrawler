package com.crawler.web.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CrawlerApplication {

	/** map used to hold token and crawling status **/
	private static ConcurrentHashMap<String,String>crawlerMap = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	public static Map<String, String> getCrawlerMap() {
		return crawlerMap;
	}

}
