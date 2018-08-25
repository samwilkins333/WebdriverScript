package WebdriverScript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static WebdriverScript.Constants.*;

public class CreateDictionary {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {

            PrintWriter writer = new PrintWriter(fileName);

            System.setProperty(chrome, driverPath);
            WebDriver driver = new ChromeDriver();
            Actions actions = new Actions(driver);

            for (char[] subset : alphabet) {
                int i = 0;

                for (char let : subset) {
                    String letter = String.valueOf(let);
                    driver.get(urlRoot + letter);

                    i++;
                    if (i == 1) {
                        actions.pause(cookieLoad).perform();
                        driver.findElement(By.xpath(dismissCookiePath)).click();
                    }

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

                    newWords.forEach(writer::println);
                }

                driver.close();
            }

            writer.close();

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.printf("Dictionary extraction complete! Process took %s seconds!\n", elapsedTime);

        } catch (Exception e) {

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.printf("UH OH! Exception hit after %s seconds!\n\n", elapsedTime);
            e.printStackTrace();

        }
    }
}