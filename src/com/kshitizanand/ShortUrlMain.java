package com.kshitizanand;

public class ShortUrlMain {

    public static void main(String[] args) {

        ShortUrl shortUrl = new ShortUrlImpl();

        // Test the Short Url Implementation by registering long URLs and looking up short URLs

        // Register long URLs and get the corresponding short URLs as return values
        String url = shortUrl.registerNewUrl("http://abc.com");
        String url1 = shortUrl.registerNewUrl("http://abc1.com");
        String url2 = shortUrl.registerNewUrl("http://abc2.com");
        String url3 = shortUrl.registerNewUrl("http://abc3.com");
        String url4 = shortUrl.registerNewUrl("http://abc2.com");  // url4 should be the same as url2
        System.out.println(url);
        System.out.println(url1);
        System.out.println(url2);
        System.out.println(url3);
        System.out.println(url4);

        // Update new URL mapping to a custom short URL
        String url5 = shortUrl.registerNewUrl("http://abc5.com", "http://short.url/test1");
        String url6 = shortUrl.registerNewUrl("http://abc6.com", "http://short.url/test2");
        // Try to update new URL to map to existing short URL, should return null
        String urlNull = shortUrl.registerNewUrl("http://abc7.com", url3);
        assert(urlNull == null);

        System.out.println(url5);
        System.out.println(url6);
        System.out.println(urlNull);

        // Test out longURL lookup based on the shortURL input
        assert(shortUrl.getUrl(url).equals("http://abc.com"));
        assert(shortUrl.getUrl(url2).equals(shortUrl.getUrl(url4)));
        assert(shortUrl.getUrl(url5).equals("http://abc5.com"));

        // Test out getHitCount() for a given long URL.
        // Here the same long URL has been looked up 2 times as part of url2 & url4
        assert(shortUrl.getHitCount("http://abc2.com").equals(2));
        // Try to fetch hit count for a non existent long URL, should return 0
        assert(shortUrl.getHitCount("http://abcn.com").equals(0));

        // From the short URL url1, remove the common section (http://short.url/) and remove any non alphanumeric character
        String choppedUrl = url1.replace("http://short.url/", "").replaceAll("[^A-Za-z0-9]", "");
        System.out.println(choppedUrl);
        // The result should have only alphanumeric characters and be 9 characters long
        assert (choppedUrl.length() == 9);

        // Delete mapping for the long URL and confirm that the short URL lookup for that long URL returns null
        shortUrl.delete("http://abc6.com");
        assert(shortUrl.getUrl(url6) == null);
    }
}
