import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDictionary {
    private static List<String> allWords = new ArrayList<>();
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final String addressRoot = "http://scrabble.merriam.com/words/start-with/";
    private static final String driverProp = "webdriver.chrome.driver";
    private static final String driverPath = "/Users/swilkinss2012/Documents/GitHub/WebdriverScript/chromedriver";
    private static final String showAllPath = "//button[@class=’sbl_load_all’]";
    private static final String wordElementPath = "//li[contains(@href, '/finder/')]";
    private static final String fileName = "scrabbledictionary.txt";

    public static void main(String[] args) throws IOException {
        System.setProperty(driverProp, driverPath);
        WebDriver driver = new ChromeDriver();

        for (char let : alphabet) {
            driver.get(addressRoot + String.valueOf(let));
            driver.findElements(By.xpath(showAllPath)).forEach(WebElement::click);
            allWords.addAll(driver.findElements(By.xpath(wordElementPath)).stream().map(WebElement::getText).collect(Collectors.toList()));
        }

       PrintWriter writer = new PrintWriter(fileName);
       allWords.forEach(writer::println);
       writer.close();
    }
}
