package com.crawler.web.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static com.crawler.web.crawler.Constants.A;
import static com.crawler.web.crawler.Constants.HREF;
import static com.crawler.web.crawler.Constants.IMG;

public class HTMLDocument {

    Logger logger = Logger.getLogger(HTMLDocument.class.getName());

    private String content;
    private int imgCount = 0;
    private int linkCount = 0;

    private String title;

    public HTMLDocument(final String content) {
        this.content = content;
    }

    /**
     * returns all the links from the current HTML document
     *
     * @return
     */
    public Set<String> getAllLinks() {

        Set<String> links = new HashSet<>();
        Document document = Jsoup.parse(this.content.trim());

        Elements elements = document.select(A);
        for(int i = 0; i<elements.size(); i++) {
            String absUrl = elements.remove(i).absUrl(HREF);
            if(!absUrl.isEmpty()) {
                links.add(absUrl);
                linkCount++;
            }
            i++;
        }

        logger.info(" .. process images ..");
        Elements imgElements = document.select(IMG);
        for(int i = 0; i<imgElements.size(); i++) {
            imgElements.remove(i);
            imgCount++;
        }

        // gets title of the page
        logger.info(" .. process titles ..");
        title = document.title();

        logger.info(".. finished ...");

        return links;
    }

    public int getImgCount() {
        return imgCount;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public String getTitle() {
        return title;
    }

}

