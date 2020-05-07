package com.crawler.web.crawler.service;

import com.crawler.web.crawler.entity.Site;

import java.util.List;

public interface CrawlerService {

    public Site saveSite(Site site);

    public List<Site> fetchAllSiteDetails();

    public List<Site> fetchAllSiteDetailsByToken(String token);
}
