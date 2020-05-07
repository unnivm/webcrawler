package com.crawler.web.crawler.repository;

import com.crawler.web.crawler.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CrawlerRepository extends JpaRepository<Site, Long> {
    public List<Site> findSitesByToken(String token);
}
