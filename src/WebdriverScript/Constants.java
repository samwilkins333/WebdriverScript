package WebdriverScript;

import java.time.Duration;

class Constants {
    static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static final Duration pause = Duration.ofSeconds(1);
    static final Duration cookieLoad = Duration.ofSeconds(7);

    static final String urlRoot = "http://scrabble.merriam.com/words/start-with/";
    static final String chrome = "webdriver.chrome.driver";
    static final String fileName = "scrabbledictionary.txt";

    static final String dismissCookiePath = ".//a[@aria-label='dismiss cookie message']";
    static final String driverPath = "/Users/swilkinss2012/Documents/GitHub/WebdriverScript/chromedriver";
    static final String expandPath = ".//div[contains(@class, 'sbl_word_group closed')]";
    static final String showAllPath = ".//button[contains(@class, 'sbl_load_all')]";
    static final String wordElementPath = ".//a[contains(@href, '/finder/')]";

    static final String username = "samwilkins";
    static final String password = "Jemmeister3!";
    static final String recipient = "14158239674";
}
