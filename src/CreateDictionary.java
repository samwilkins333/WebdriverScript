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
    private static final String expandPath = ".//i[contains(@class, 'plus-square-o')]";
    private static final String showAllPath = ".//button[contains(@class, 'sbl_load_all')]";
    private static final String wordElementPath = ".//a[contains(@href, '/finder/')]";
    private static final String fileName = "scrabbledictionary.txt";

    public static void main(String[] args) {
        System.setProperty(chrome, driverPath);
        WebDriver driver = new ChromeDriver();

        for (char let : alphabet) {
            String letter = String.valueOf(let);
            driver.get(urlRoot + letter);

            System.out.printf("\n*** %s ***\n\n", letter);
            List<WebElement> categories = driver.findElements(By.xpath(expandPath));
            System.out.printf("Page has %s categories to expand...", categories.size());
            List<WebElement> visibleCategories = categories.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            System.out.printf("...of which %s are visible.\n", visibleCategories.size());
            visibleCategories.forEach(WebElement::click);

            List<WebElement> showAll = driver.findElements(By.xpath(showAllPath));
            System.out.printf("Page has %s 'show all' buttons...", showAll.size());
            List<WebElement> visibleShowAll = showAll.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            System.out.printf("...of which %s are visible.\n", visibleShowAll.size());
            visibleShowAll.forEach(WebElement::click);

            List<String> newWords = driver.findElements(By.xpath(wordElementPath)).stream().map(WebElement::getText).filter(s -> !s.equals("")).collect(Collectors.toList());
            System.out.printf("Letter %s had %s words\n", let, newWords.size());

            allWords.addAll(newWords);
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
