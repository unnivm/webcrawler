package com.crawler.web.crawler;

import com.crawler.web.crawler.entity.Site;
import com.crawler.web.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLDocument {

    private String content;
    private int imgCount = 0;
    private int linkCount = 0;

    String regexp = "(img|http|https)://(\\w+\\.)+(edu|com|gov|org|img)";
    Pattern pattern = Pattern.compile(regexp);
    Pattern imgPatten = Pattern.compile(".*<img[^>]*src=\"([^\"]*)", Pattern.CASE_INSENSITIVE);


    private final Pattern titlePattern =
            Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private String title;

    public HTMLDocument(final String content) {
        this.content = content;
    }

    /**
     * returns all the links from the current HTML document
     *
     * @return
     */
    public List<String> getAllLinks() {
        List<String> links = new ArrayList<>();

        // gets title of the page
        Matcher titleMatcher = titlePattern.matcher(content == null ? "" : content);
        while (titleMatcher != null && titleMatcher.find()) {
            title = titleMatcher.group();
            System.out.println(" title " + title);
        }

        // gets links from the current page
        Matcher matcher = pattern.matcher(content == null ? "" : content);
        while (matcher != null && matcher.find()) {
            linkCount++;
            String w = matcher.group();
            links.add(w);
        }

        // gets the total image from the current page
        Matcher imgMatcher = imgPatten.matcher(content == null ? "" : content);
        while (imgMatcher != null && imgMatcher.find()) {
            String w = imgMatcher.group();
            if (w != null && !w.isEmpty())
                imgCount++;
        }

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

