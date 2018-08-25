package WebdriverScript;

import com.textmagic.sms.TextMagicMessageService;
import com.textmagic.sms.exception.ServiceBackendException;
import com.textmagic.sms.exception.ServiceTechnicalException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static WebdriverScript.Constants.*;

public class CreateDictionary {

    private static TextMagicMessageService service;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {

            PrintWriter writer = new PrintWriter(fileName);
            service = new TextMagicMessageService(username, password);

            System.setProperty(chrome, driverPath);
            WebDriver driver = new ChromeDriver();
            Actions actions = new Actions(driver);

            for (char let : alphabet) {
                String letter = String.valueOf(let);
                driver.get(urlRoot + letter);

                if (letter.equals("a")) {
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

                String message = String.format("Letter %s had %s non-empty words.\n", letter.toUpperCase(), newWords.size());
                service.send(message, recipient);
            }

            writer.close();

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            String message = String.format("Dictionary extraction complete! Process took %s seconds!", elapsedTime);
            service.send(message, recipient);

        } catch (FileNotFoundException | ServiceBackendException | ServiceTechnicalException e) {

            e.printStackTrace();

        } catch (TimeoutException t) {

            try {

                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                String stackTrace = Arrays.toString(t.getStackTrace());
                String message = String.format("*** Oh no! Script timed out after %s seconds *** \n\n%s", elapsedTime, stackTrace);
                service.send(message, recipient);

            } catch (ServiceBackendException | ServiceTechnicalException e) {

                e.printStackTrace();

            }

        }
    }
}