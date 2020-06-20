# webcrawler
Small web crawler developed in Java and Spring Boot.

This crawler is a multi-threaded one which can start multiple crawling. 

#How this works:

This is a REST based crawler and crawling a web site can be started using a REST end point.

1. Start with a seed web site to crawl and depth to crawl. This is a "POST" request

  http://localhost:8080/start?depth=5&seed=http://www.google.com
  
  Once it is requested, it will generate a "token". This token can be used to query the status of the crawling operation
  
2. You can use another endpoint to check the status of the crawling:

  http://localhost:8080/status/<token> which will generate a JSON response.
  
3. This  endpoint which will give you the result of the crawling.

   http://localhost:8080/result/<token>
  
4. The following endpoint will cancel the current crawling task

   http://localhost:8080/stop/token

  
  
