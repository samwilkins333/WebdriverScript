import com.textmagic.sms.exception.ServiceBackendException;
import com.textmagic.sms.exception.ServiceTechnicalException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.textmagic.sms.*;

public class CreateDictionary {
    private static List<String> allWords = new ArrayList<>();
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static Duration pause = Duration.ofSeconds(1);

    private static final String urlRoot = "http://scrabble.merriam.com/words/start-with/";
    private static final String chrome = "webdriver.chrome.driver";
    private static final String driverPath = "/Users/swilkinss2012/Documents/GitHub/WebdriverScript/chromedriver";
    private static final String expandPath = ".//div[contains(@class, 'sbl_word_group closed')]";
    private static final String showAllPath = ".//button[contains(@class, 'sbl_load_all')]";
    private static final String wordElementPath = ".//a[contains(@href, '/finder/')]";
    private static final String fileName = "scrabbledictionary.txt";

    private static final String recipient = "14158239674";


    public static void main(String[] args) {
        System.setProperty(chrome, driverPath);
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        TextMagicMessageService service = new TextMagicMessageService("", "");

        for (char let : alphabet) {
            String letter = String.valueOf(let);
            driver.get(urlRoot + letter);

            System.out.printf("\n*** %s ***\n\n", letter.toUpperCase());
            List<WebElement> categories = driver.findElements(By.xpath(expandPath));
            System.out.printf("Page has %s categories to expand.\n", categories.size());
            categories.forEach(webElement -> {
                actions.moveToElement(webElement).pause(pause).perform();
                webElement.click();
                actions.pause(pause).perform();
            });

            List<WebElement> showAll = driver.findElements(By.xpath(showAllPath));
            System.out.printf("Page has %s 'show all' buttons.\n", showAll.size());
            showAll.forEach(webElement -> {
                actions.moveToElement(webElement).pause(pause).perform();
                webElement.click();
                actions.pause(pause).perform();
            });

            List<String> newWords = driver.findElements(By.xpath(wordElementPath)).stream().map(WebElement::getText).filter(s -> !s.equals("")).collect(Collectors.toList());
            System.out.printf("Letter %s had %s non-empty words.\n", letter.toUpperCase(), newWords.size());

            allWords.addAll(newWords);

            try {

                service.send(String.format("Finished processing the %s's", letter.toUpperCase()), recipient);

            } catch (ServiceBackendException | ServiceTechnicalException e) {

                e.printStackTrace();

            }
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
