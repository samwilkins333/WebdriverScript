package CreateDictionary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static CreateDictionary.Constants.*;

class ConfirmCount {

    public static void main(String[] args) {
        System.setProperty(chrome, driverPath);
        WebDriver driver = new ChromeDriver();
        int sum = 0;

        for (char c : A) {
            String letter = String.valueOf(c);
            System.out.printf("\n*** %s ***\n\n", letter.toUpperCase());
            driver.get(scrabbleRoot + letter);

            List<WebElement> counts = driver.findElements(By.xpath(countPath));
            for (WebElement webElement : counts) {
                int next = Integer.parseInt(webElement.getText().split(" ")[0].trim());
                sum += next;
                System.out.println(next);
            }
        }

        System.out.printf("\nDictionary should contain %s terms\n", sum);
    }

}
