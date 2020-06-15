package com.crawler.web.crawler;

import com.crawler.web.crawler.entity.Site;
import com.crawler.web.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.crawler.web.crawler.Constants.COMPLETED;
import static  com.crawler.web.crawler.Constants.PROCESSING;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class will crawl web sites and will extract the links
 * These links again will be used to crawl. During crawling
 * it will calculate the total links images and insert into
 * the table.
 *
 * The crawling will perform either there are no more links to
 * crawl or depth is reached
 *
 * The Async annotation is used to perform the crawling task as asynchronous
 * and it will not block
 *
 * This class can be used to process multiple crawling requests
 * Once the crawling completed, it will update the status in a map
 *
 */
@Component
public class CrawlerTask {

    private Logger log = Logger.getLogger(CrawlerTask.class.getName());

    @Autowired
    CrawlerService crawlerServie;

    @Async
    public void crawl(final String url, final int d, final String id) {
        CrawlerApplication.getCrawlerMap().put(id, PROCESSING);

        final LinkedList<String> queue = new LinkedList<>();
        queue.add(url);

        int depthCount = 0;
        while(!queue.isEmpty()) {
            CrawlerApplication.getCrawlerMap().put(id, "processing");

            if(depthCount == d) {
                log.info(" user depth reached..");
                break;
            }

            String u  = queue.poll();

            WebSite webSite = new WebSite(u);
            String response = webSite.crawl();
            HTMLDocument htmlDocument = new HTMLDocument(response);
            List<String> links        = htmlDocument.getAllLinks();
            queue.addAll(links);

            // save this crawler information to the database
            Site site  = new Site();
            site.setToken(id);
            site.setTitle(htmlDocument.getTitle());
            site.setTotalImage(htmlDocument.getImgCount());
            site.setTotalLinks(htmlDocument.getLinkCount());
            crawlerServie.saveSite(site);

            depthCount++;

            log.info(" depth  " + depthCount);

            // wait
            justWaitForSomeTime();
        }

        CrawlerApplication.getCrawlerMap().put(id, COMPLETED);
        log.info("..FINISHED CRAWLING SITES for " + url);
    }

    private void justWaitForSomeTime() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
