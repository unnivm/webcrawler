package com.crawler.web.crawler.service.impl;

import com.crawler.web.crawler.entity.Site;
import com.crawler.web.crawler.repository.CrawlerRepository;
import com.crawler.web.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    CrawlerRepository crawlerRepository;

    @Override
    public Site saveSite(Site site) {
        return crawlerRepository.save(site);
    }

    @Override
    public List<Site> fetchAllSiteDetails() {
        return crawlerRepository.findAll();
    }

    @Override
    public List<Site> fetchAllSiteDetailsByToken(String token) {
        return crawlerRepository.findSitesByToken(token);
    }

}
