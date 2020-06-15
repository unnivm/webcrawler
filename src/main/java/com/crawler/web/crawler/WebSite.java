package com.crawler.web.crawler;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class WebSite {

    final Logger logger = Logger.getLogger(WebSite.class.getName());

    private String url;
    public WebSite(final String url) {
        this.url = url;
    }

    /**
     * Connects to website and gets its stream and returns
     * the response
     *
     * @return
     */
    public String crawl() {
        try {
            URL site = new URL(url);
        URLConnection connection = url.startsWith("https") ?
                (HttpsURLConnection) site.openConnection() :
                (HttpURLConnection) site.openConnection() ;

        int code = getSiteResponseCode(connection);

        if(code == 200) {
            InputStream inputStream = connection.getInputStream();
            return readResponse(inputStream);
        }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "";
    }

    private int getSiteResponseCode(URLConnection urlConnection) throws IOException {
            return urlConnection instanceof HttpURLConnection
                    ? ((HttpURLConnection)urlConnection).getResponseCode()
                    : ((HttpsURLConnection)urlConnection).getResponseCode();

    }

    /**
     * Read input stream and returns response
     *
     * @param is
     * @return
     */
    private String readResponse(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = reader.readLine())!= null) {
            response.append(line).append("\n");
        }
        if(is != null) is.close();

        logger.fine(".. processed response .. ");
        return response.toString();
    }

}
