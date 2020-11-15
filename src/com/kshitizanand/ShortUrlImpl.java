package com.kshitizanand;

import java.util.HashMap;
import java.util.Random;

public class ShortUrlImpl implements ShortUrl{
    private HashMap<String, String> keyMap = new HashMap<String, String>();
    private HashMap<String, String> valueMap = new HashMap<String, String>();
    private HashMap<String, Integer> hitCountMap = new HashMap<String, Integer>();
    private String domain = "http://short.url";
    private char myChars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private Random myRand = new Random();
    private int keyLength = 9;

    boolean validateURL(String url) {
        return true;
    }

    String sanitizeURL(String url) {
        if (url.length() > 7 && url.substring(0, 7).equals("http://"))
            url = url.substring(7);

        if (url.length() > 8 && url.substring(0, 8).equals("https://"))
            url = url.substring(8);

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }

    private String getKey(String longUrl) {
        String key;
        key = generateKey();
        keyMap.put(key, longUrl);
        valueMap.put(longUrl, key);
        return key;
    }

    private String generateKey() {
        String key = "";
        boolean flag = true;
        while (flag) {
            key = "";
            for (int i = 0; i < keyLength; i++) {
                key += myChars[myRand.nextInt(62)];
            }

            if (!keyMap.containsKey(key)) {
                flag = false;
            }
        }
        return key;
    }

    public String registerNewUrl(String longUrl) {
        String shortUrl = "";
        if (validateURL(longUrl)) {
            longUrl = sanitizeURL(longUrl);
            if (valueMap.containsKey(longUrl)) {
                shortUrl = domain + "/" + valueMap.get(longUrl);
            }
            else {
                shortUrl = domain + "/" + getKey(longUrl);
            }
        }

        return shortUrl;
    }

    public String getUrl(String shortUrl) {
        String longUrl = "";
        String key = shortUrl.substring(domain.length() + 1);

        if(keyMap.containsKey(key)) {
            longUrl = "http://" + keyMap.get(key);
            setHitCount(longUrl, 1);
            return longUrl;
        }
        else {
            return null;
        }

    }

    public String delete(String longUrl) {
        longUrl = sanitizeURL(longUrl);
        String key = valueMap.get(longUrl);

        if (keyMap.containsKey(key)) {
            keyMap.remove(key);
        }

        return "";
    }

    public String registerNewUrl(String longUrl, String shortUrl) {
        String key = shortUrl.substring(domain.length() + 1);
        longUrl = sanitizeURL(longUrl);
        if (keyMap.containsKey(key)) {
            return null;
        }
        else {
            keyMap.put(key, longUrl);
            valueMap.put(longUrl, key);
            return shortUrl;
        }
    }

    public void setHitCount(String longUrl, int hitCount) {
        if (hitCountMap.containsKey(longUrl)) {
            hitCountMap.put(longUrl, hitCountMap.get(longUrl) + hitCount);
        }
        else {
            hitCountMap.put(longUrl, hitCount);
        }
    }

    public Integer getHitCount(String longUrl) {
        int hitCount;
        if (hitCountMap.containsKey(longUrl)) {
            hitCount = hitCountMap.get(longUrl);
            return hitCount;
        }
        else {
            return 0;
        }
    }
}
