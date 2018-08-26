package CreateDictionary;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

class Constants {
    static final List<char[]> alphabet;
    static {
        alphabet = new ArrayList<>();
        alphabet.add(new char[] {'a', 'b', 'c'});
        alphabet.add(new char[] {'d', 'e', 'f'});
        alphabet.add(new char[] {'g', 'h', 'i'});
        alphabet.add(new char[] {'j', 'k', 'l'});
        alphabet.add(new char[] {'m', 'n', 'o'});
        alphabet.add(new char[] {'p', 'q', 'r'});
        alphabet.add(new char[] {'s', 't', 'u'});
        alphabet.add(new char[] {'v', 'w', 'x'});
        alphabet.add(new char[] {'y', 'z'});
    }

    static final char[] A = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    static final Duration pause = Duration.ofSeconds(1);
    static final Duration cookieLoad = Duration.ofSeconds(7);

    static final String urlRoot = "http://scrabble.merriam.com/words/start-with/";
    static final String chrome = "webdriver.chrome.driver";
    static final String fileName = "scrabbledictionary2.txt";

    static final String dismissCookiePath = ".//a[@aria-label='dismiss cookie message']";
    static final String driverPath = "/Users/swilkinss2012/Documents/GitHub/WebdriverScript/chromedriver";
    static final String expandPath = ".//div[contains(@class, 'sbl_word_group closed')]";
    static final String showAllPath = ".//button[contains(@class, 'sbl_load_all')]";
    static final String wordElementPath = ".//a[contains(@href, '/finder/')]";
    static final String countPath = ".//span[contains(text(), 'found')]";
}
