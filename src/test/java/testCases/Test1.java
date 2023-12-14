package testCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test1 {

	@Test
	public void demo() {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.office.com/");
//		Actions action = new Actions(driver);
		String link =driver.findElement(By.id("hero-banner-sign-in-microsoft-365-link")).getAttribute("href");
		driver.get(link);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("i0116"))));
		driver.findElement(By.id("i0116")).sendKeys("pratik.qualitykiosk@tataaig.com");
		driver.findElement(By.id("idSIButton9")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("content"))));
		driver.findElement(By.id("passwordInput")).sendKeys("dummy_pass");
		
		

	
	}

}
