package pageClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractComponents {

	WebDriver driver;
//	WebDriverWait wait = new WebDriverWait(driver, Duration.)

	@FindBy(id = "user-name")
	private WebElement Username;

	@FindBy(css = "button[type='submit']")
	private WebElement SubmitButton;

	@FindBy(css = ".btn")
	private WebElement ConfirmButton;

	@FindBy(css = "ng-select[role='listbox']")
	private WebElement LocationDropDown;

	@FindBy(css = ".btn")
	private WebElement SignInButton;

	@FindBy(css = "input[placeholder='Password']")
	private WebElement UserPassword;

	@FindBy(css = ".ng-option-label")
	private List<WebElement> Branches;

	@FindBy(xpath = "//mr-4 //span")
	private WebElement HealtProducts;

	@FindBy(xpath = "(//div[@class='card-text-wrapper'] //span[contains(text(),'Health')])[1]")
	private WebElement HealthTemplate;

	@FindBy(css = "div[class='row ng-star-inserted']")
	private WebElement HomePageTemplate;

	@FindBy(css = ".swal2-confirm")
	private WebElement Alert;
	WebDriverWait wait;

	public LoginPage(WebDriver driver) throws FileNotFoundException, IOException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		Duration timeout = Duration.ofSeconds(20);
		wait = new WebDriverWait(driver, timeout);
	}

	public void Login() throws FileNotFoundException, IOException, InterruptedException {

		driver.get(prop.getProperty("url"));
		Username.sendKeys(prop.getProperty("username"));
		SubmitButton.click();
		wait.until(ExpectedConditions.visibilityOf(UserPassword));
		UserPassword.sendKeys(prop.getProperty("password"));
		SignInButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(LocationDropDown));
		LocationDropDown.click();
//		Actions act = new Actions(driver);
		action.sendKeys(LocationDropDown, prop.getProperty("location")).build().perform();
		Branches.stream().filter(branch -> branch.getText().equalsIgnoreCase(prop.getProperty("location"))).findFirst()
				.orElse(null).click();
		wait.until(ExpectedConditions.elementToBeClickable(ConfirmButton));
		ConfirmButton.click();
		wait.until(ExpectedConditions.visibilityOf(HomePageTemplate));
		Thread.sleep(9000);
		try {
			HealthTemplate.click();
		} catch (Exception e) {
			System.out.println("Refreshing the page due to incomplete loading !!");
			action.keyDown(Keys.CONTROL).keyDown(Keys.F5).build().perform();
			Thread.sleep(9000);
			HealthTemplate.click();
		}
		
	}

}
