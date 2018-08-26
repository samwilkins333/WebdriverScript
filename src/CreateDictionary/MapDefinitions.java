package CreateDictionary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static CreateDictionary.Constants.*;

public class MapDefinitions {

    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter(defWriteFileName);
            Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("dictionary.txt"));

            System.setProperty(chrome, driverPath);
            WebDriver driver = new ChromeDriver();

            while (scanner.hasNext()) {
                String thisWord = scanner.next();
                System.out.printf("\nWorking on \"%s\"\n", thisWord);
                driver.get(merriamRoot + thisWord);

                List<WebElement> defs = driver.findElements(By.xpath(definitionPath));
                List<WebElement> parts = driver.findElements(By.xpath(partPath));

                String definition = defs.size() > 0 ? defs.get(0).getText().replace(":", "").trim() : "*** UNABLE TO PARSE DEFINITION ***";
                String part = parts.size() > 0 ? parts.get(0).getText().trim().toLowerCase() : "*** N/A ***";

                String out = String.format("%s - %s : %s", thisWord, part, definition);
                writer.println(out);
                System.out.println(out);
            }

            driver.close();
            writer.close();
            scanner.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }
    }

}
