import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDictionary {
    private static List<String> allWords = new ArrayList<>();
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final String urlRoot = "http://scrabble.merriam.com/words/start-with/";
    private static final String chrome = "webdriver.chrome.driver";
    private static final String driverPath = "/Users/swilkinss2012/Documents/GitHub/WebdriverScript/chromedriver";
    private static final String showAllPath = "//button[@class=’sbl_load_all’]";
    private static final String wordElementPath = "//li[contains(@href, '/finder/')]";
    private static final String fileName = "scrabbledictionary.txt";

    public static void main(String[] args) {
        System.setProperty(chrome, driverPath);
        WebDriver driver = new ChromeDriver();

        for (char let : alphabet) {
            driver.get(urlRoot + String.valueOf(let));
            driver.findElements(By.xpath(showAllPath)).forEach(WebElement::click);
            allWords.addAll(driver.findElements(By.xpath(wordElementPath)).stream().map(WebElement::getText).collect(Collectors.toList()));
        }

        try {
            PrintWriter writer = new PrintWriter(fileName);
            allWords.forEach(writer::println);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
