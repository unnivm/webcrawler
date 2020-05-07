package com.crawler.web.crawler.dto;

import com.crawler.web.crawler.entity.Site;

import java.util.List;

public class CrawlerDTO {

    private int totalLinks;
    private int totalImages;
    private List<SiteDTO> details;

    public int getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }

    public List<SiteDTO> getDetails() {
        return details;
    }

    public void setDetails(List<SiteDTO> details) {
        this.details = details;
    }
}
