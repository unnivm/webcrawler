package com.crawler.web.crawler.controller;

import com.crawler.web.crawler.CrawlerTask;
import com.crawler.web.crawler.CrawlerApplication;
import com.crawler.web.crawler.dto.CrawlerDTO;
import com.crawler.web.crawler.dto.SiteDTO;
import com.crawler.web.crawler.dto.TokenDTO;
import com.crawler.web.crawler.entity.Site;
import com.crawler.web.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.crawler.web.crawler.Constants.JSON;
import static com.crawler.web.crawler.Constants.INPROCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@RestController
public class CrawlerController {

    private Logger log = Logger.getLogger(CrawlerController.class.getName());

    @Autowired
    CrawlerTask crawlerTask;

    @Autowired
    CrawlerService crawlerService;

    /**
     * Creates a crawling request based on depth and seed
     *
     * @param depth
     * @param seedURL
     * @return
     */
    @PostMapping("/start")
    public ResponseEntity createCrawlerRequest(@RequestParam("depth") String depth, @RequestParam("seed") String seedURL) {
        log.info(" depth " + depth);
        log.info(" seed " + seedURL);

        String token = UUID.randomUUID().toString();
        CrawlerApplication.getCrawlerMap().put(token, INPROCESS);

        Future<String> result = crawlerTask.crawl(seedURL, Integer.parseInt(depth), token);

        // saves the result
        CrawlerApplication.getCrawlerTask().put(token, result);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setStatus(INPROCESS);
        tokenDTO.setToken(token);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/status/{token}", produces = JSON)
    public ResponseEntity getStatus(@PathVariable("token") String token) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setStatus(CrawlerApplication.getCrawlerMap().get(token));
        tokenDTO.setToken(token);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    /**
     * this method will get the list of the crawled web site with total links and images
     *
     * @param token
     * @return
     */
    @GetMapping("/result/{token}")
    public ResponseEntity<Site> listAllSites(@PathVariable("token") String token) {

        List<Site> list = crawlerService.fetchAllSiteDetailsByToken(token);
        int imgCount = 0;
        int linkCount = 0;
        for (Site site : list) {
            imgCount += site.getTotalImage();
            linkCount += site.getTotalLinks();
        }

        List<SiteDTO> details = new ArrayList<>();
        for (Site site : list) {
            SiteDTO siteDTO = new SiteDTO();
            siteDTO.setImageCount(site.getTotalImage() + "");
            siteDTO.setPageLink(site.getTotalImage() + "");
            siteDTO.setPageTitle(site.getTitle());

            details.add(siteDTO);
        }

        CrawlerDTO crawlerDTO = new CrawlerDTO();
        crawlerDTO.setTotalLinks(linkCount);
        crawlerDTO.setTotalImages(imgCount);
        crawlerDTO.setDetails(details);

        return new ResponseEntity(crawlerDTO, HttpStatus.OK);
    }

    /**
     * cancel current crawling task and removes from the map
     * @param token
     */
    @DeleteMapping("/stop/{token}")
    public ResponseEntity<String> stopCrawling(@PathVariable("token") String token) {
        Map<String, Future> map = CrawlerApplication.getCrawlerTask();
        Future<String>task = map.get(token);
        if(task!= null && !task.isDone()) {
            task.cancel(true);
            map.remove(token);
            log.fine(".. crawling cancelled for this token .. " + token );
            return new ResponseEntity<String>("the crawling has been cancelled for this token " + token, HttpStatus.OK);
        }
        return new ResponseEntity<String>("This token is invalid " + token, HttpStatus.OK);
    }

}
