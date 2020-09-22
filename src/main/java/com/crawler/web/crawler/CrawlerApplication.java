package com.crawler.web.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAsync
public class CrawlerApplication {

	/** map used to hold token and crawling status **/
	private static ConcurrentHashMap<String,String>crawlerMap = new ConcurrentHashMap<>();

	/** map used to save current crawling task **/
	private static ConcurrentHashMap<String, Future> list = new ConcurrentHashMap<>();


	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	/**
	 * return crawler map
	 * @return
	 */
	public static Map<String, String> getCrawlerMap() {
		return crawlerMap;
	}

	/**
	 * return crawler task map
	 * @return
	 */
	public static ConcurrentHashMap<String,Future> getCrawlerTask() {
		return list;
	}


}
