import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SaveDailyURLs {
    public static void main(String[] args) throws IOException {
        String root = "https://www.thehindu.com/archive/web/2010/";
        List<String> monthly = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthly.add(root + "0" + i);
            } else {
                monthly.add(root + i);
            }

        }
        int iYear = 2010;
        List<String> daily = new ArrayList<String>();

        for (String str : monthly) {
            String[] month = str.split("/");
            int monthInt = Integer.parseInt(month[month.length - 1]) - 1;
            int iDay = 1;

            Calendar mycal = new GregorianCalendar(iYear, monthInt, iDay);

            int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= daysInMonth; i++) {
                if (i < 10) {
                    daily.add(str + "/0" + i);

                } else {
                    daily.add(str + "/" + i);
                }
            }
        }
        System.out.println(daily.toArray().length);


        System.setProperty("webdriver.chrome.driver", "C:\\Users\\razor1911\\IdeaProjects\\hinduscrap\\res\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        List<String> links = new ArrayList<String>();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);



        for (int u=100;u<daily.size();u++) {
            System.out.println(u);
            String baseUrl = daily.get(u);
            driver.get(baseUrl);

            List<WebElement> allLinks = driver.findElements(By.tagName("a"));

            System.out.println("All links found on web page are: " + allLinks.size() + " links");
            for (WebElement link : allLinks) {
                String lnk = link.getAttribute("href");
                if (lnk != null) {
                    if (lnk.endsWith("ece")) {
                        links.add(lnk);
                    }
                }
            }

        }
        driver.close();
        FileWriter writer = new FileWriter("C:\\Users\\razor1911\\IdeaProjects\\hinduscrap\\res\\links.csv");

        String collect = links.stream().collect(Collectors.joining(","));

        writer.write(collect);
        writer.close();

    }
}
