package pageClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {

	WebDriver driver;
	Properties prop = new Properties();
	WebDriverWait wait;
	Actions action;

	@FindBy(xpath = "//select[@title='Select year'] //option[@class='ng-star-inserted']")
	static List<WebElement> ListOfAllYears;

	@FindBy(xpath = "//select[@title='Select month'] //option[@class='ng-star-inserted']")
	static List<WebElement> ListOfAllMonths;

	@FindBy(css = ".btn-light")
	static List<WebElement> ListOfAllDates;
	
	@FindBy (css = "div[role='option']")
	private		List<WebElement> AllDropDownValues;

	protected AbstractComponents(WebDriver driver) throws FileNotFoundException, IOException {
		this.driver = driver;
		Duration timeout = Duration.ofSeconds(25);
		wait = new WebDriverWait(driver, timeout);
		PageFactory.initElements(driver, this);
		prop.load(new FileInputStream(
				new File(System.getProperty("user.dir") + "\\src\\main\\java\\TestData\\global.properties")));
		 action = new Actions(this.driver);
	}

	protected void pageScreenshot(String testcasename) {

		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File(System.getProperty("user.dir") + "\\src\\main\\java\\testScreenshots\\" + testcasename));
		} catch (WebDriverException e) {
			System.out.println("Could not find driver");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

	}

	protected void elementScreenshotS(String testcasename, WebElement element) throws WebDriverException, IOException {

		File file = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file,
				new File(System.getProperty("user.dir") + "\\src\\main\\java\\testScreenshots1\\demo.png"));

	}

	protected void findElement(String word) throws InterruptedException {
		Actions act = new Actions(driver);
		
		act.keyDown(Keys.CONTROL).sendKeys("f").keyUp(Keys.CONTROL).build().perform();
//		String control = Keys.chord(Keys.CONTROL, "F");
//		act.sendKeys(control).build().perform();
		Thread.sleep(2000);
		act.sendKeys(word).build().perform();	
		act.sendKeys(Keys.ESCAPE).build().perform();
	}

	protected void scrollPageUp() {
		action.sendKeys(Keys.PAGE_UP).build().perform();
	}
	protected void scrollPageDown() {
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		
	}
	protected void scrollPageToEnd() {
		action.sendKeys(Keys.END).build().perform();
	}
	protected void refreshPage() {
		Actions act = new Actions(driver);
		act.keyDown(Keys.CONTROL).sendKeys("R").build().perform();
	}

	protected synchronized static void dateSelection(WebElement insuredTemplate, String DOB) throws InterruptedException {
		System.out.println("Driver reached");

		String[] date = DOB.split("/");
		Map<String, String> NumberToMonth = new HashMap<String, String>();
		NumberToMonth.put("01", "Jan");
		NumberToMonth.put("02", "Feb");
		NumberToMonth.put("03", "Mar");
		NumberToMonth.put("04", "Apr");
		NumberToMonth.put("05", "May");
		NumberToMonth.put("06", "Jun");
		NumberToMonth.put("07", "Jul");
		NumberToMonth.put("08", "Aug");
		NumberToMonth.put("09", "Sep");
		NumberToMonth.put("10", "Oct");
		NumberToMonth.put("11", "Nov");
		NumberToMonth.put("12", "Dec");

		insuredTemplate.findElement(By.className("svg-sm")).click();
		for(String a:date) {
			System.out.println(a);
		}

		ListOfAllYears.stream().filter(s -> s.getText().contains(date[2])).findFirst().orElse(null).click();

		ListOfAllMonths.stream().filter(s -> s.getText().contains(NumberToMonth.get(date[1]))).findFirst().orElse(null)
				.click();

		WebElement dataSelected = ListOfAllDates.stream().filter(s -> s.getText().contains(date[0])).findFirst()
				.orElse(null);

		dataSelected.click();

	}

	protected void selectDropdownValue(WebElement TargetElement, String selectValue) {
		TargetElement.click();
		wait.until(ExpectedConditions.visibilityOfAllElements(AllDropDownValues));
		System.out.println(AllDropDownValues);
		AllDropDownValues.stream().filter(value -> value.getText().contains(selectValue))
				.findFirst().orElse(null).click();
	}

}
