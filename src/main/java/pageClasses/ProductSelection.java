package pageClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductSelection extends AbstractComponents {

	WebDriver driver;
	WebDriverWait wait;
	HashMap<String, String> quotedata;
	@FindBy(css = ".theme-value")
	private List<WebElement> AllProducts;

	@FindBy(css = ".swal2-confirm")
	private WebElement Alert;

	@FindBy(xpath = "(//div[@class='card-content bigcard'] //div[@class='d-flex align-items-center']  //button[contains(text(),'Features')])[6]")
	private WebElement BoltFeaturesButton;

	@FindBy(css = "div[class*='modal-body succes-container']")
	private WebElement BoltContainer;

	@FindBy(css = "div[title='BOLT']")
	private WebElement BoltButton;

	@FindBy(css = "div[class='MuiBox-root css-10626ex']")
	private WebElement InsuredTemplate;

	@FindBy(css = "div[class='MuiBox-root css-dvxtzn']")
	private List<WebElement> SelectIndividualPageAllInsuredButton;

	@FindBy(xpath = "//button[contains(text(),'Next')]")
	private WebElement SelectIndividualPageNextButton;

	@FindBy(css = "div[class*='MuiBox-root css-1rj3um1']")
	private WebElement PersonalDetailsPageBox;

	@FindBy(css = "div[class*='MuiFormControl-root MuiFormControl-fullWidth MuiTextField-root css-1oz32il'] div input")
	private List<WebElement> PersonalDetailsAllInsuredDOB;
	
	@FindBy (xpath = "//button[contains(text(),'Generate Quote')]")
	private WebElement PersonalDetailsGenerateQuote;
	
	@FindBy (css = "div[class='MuiBox-root css-o37qtr']")
	private WebElement SuggestedProductsBox;
	
	public ProductSelection(WebDriver driver, HashMap<String, String> quotedata)
			throws FileNotFoundException, IOException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		Duration timeout = Duration.ofSeconds(20);
		wait = new WebDriverWait(driver, timeout);
		this.quotedata = quotedata;
	}

	public void selectProduct() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(Alert));
		Alert.click();
		Thread.sleep(2000);
		BoltFeaturesButton.click();
		String mainWindowHandle = driver.getWindowHandle();
		wait.until(ExpectedConditions.visibilityOf(BoltContainer));
		BoltButton.click();
		Thread.sleep(15000);
		// Actions action = new Actions(driver);
		// action.keyDown(Keys.CONTROL).click().build().perform();
		// action.keyDown(Keys.CONTROL).keyDown(Keys.PAGE_DOWN).build().perform();
		// Thread.sleep(2000);
		Set<String> Windows = driver.getWindowHandles();

		for (String handle : Windows) {
			if (!mainWindowHandle.equals(handle)) {
				driver.switchTo().window(handle);
				Thread.sleep(20000);
//				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("img[alt='Self']"))));
//				driver.findElement(By.cssSelector("img[alt='Self']")).click();
				break;
			}

		}
		insuredSelection();
		scrollPageToEnd();
		SelectIndividualPageNextButton.click();
		wait.until(ExpectedConditions.visibilityOf(PersonalDetailsPageBox));
		personalDetailsInsuredDOB();
		wait.until(ExpectedConditions.elementToBeClickable(PersonalDetailsGenerateQuote));
		PersonalDetailsGenerateQuote.click();
		wait.until(ExpectedConditions.visibilityOf(SuggestedProductsBox));
		

	}

	private void insuredSelection() {
		if (quotedata.get("IsSelfIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Self"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsSpouseIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Spouse"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsFatherIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Father"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsMotherIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Mother"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsFatherInLawIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Father In Law"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsMotherInLawIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Mother In Law"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsGrandFatherIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Grand Father"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsGrandMotherIncluded").equalsIgnoreCase("Yes")) {
			SelectIndividualPageAllInsuredButton.stream().filter(insured -> insured.getText().contains("Grand Mother"))
					.findFirst().orElse(null).click();
		}
		if (quotedata.get("IsSon1Included").equalsIgnoreCase("Yes")) {
			WebElement Son = SelectIndividualPageAllInsuredButton.stream()
					.filter(insured -> insured.getText().contains("Son")).findFirst().orElse(null);
			Son.click();
			if (quotedata.get("IsSon2Included").equalsIgnoreCase("Yes")) {
				wait.until(ExpectedConditions
						.elementToBeClickable(Son.findElement(By.cssSelector("svg[data-testid='AddIcon']"))));
				Son.findElement(By.cssSelector("svg[data-testid='AddIcon']")).click();
				if (quotedata.get("IsSon3Included").equalsIgnoreCase("Yes")) {
					wait.until(ExpectedConditions
							.elementToBeClickable(Son.findElement(By.cssSelector("svg[data-testid='AddIcon']"))));
					Son.findElement(By.cssSelector("svg[data-testid='AddIcon']")).click();
				}

			}
		}
		if (quotedata.get("IsDaughter1Included").equalsIgnoreCase("Yes")) {
			WebElement Daughter = SelectIndividualPageAllInsuredButton.stream()
					.filter(insured -> insured.getText().contains("Daughter")).findFirst().orElse(null);
			Daughter.click();
			if (quotedata.get("IsDaughter2Included").equalsIgnoreCase("Yes")) {
				wait.until(ExpectedConditions
						.elementToBeClickable(Daughter.findElement(By.cssSelector("svg[data-testid='AddIcon']"))));
				Daughter.findElement(By.cssSelector("svg[data-testid='AddIcon']")).click();
				if (quotedata.get("IsDaughter3Included").equalsIgnoreCase("Yes")) {
					wait.until(ExpectedConditions
							.elementToBeClickable(Daughter.findElement(By.cssSelector("svg[data-testid='AddIcon']"))));
					Daughter.findElement(By.cssSelector("svg[data-testid='AddIcon']")).click();
				}

			}
		}
	}

	private void personalDetailsInsuredDOB() {
		if (quotedata.get("IsSelfIncluded").equalsIgnoreCase("Yes") && quotedata.get("SelfDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
					.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsSpouseIncluded").equalsIgnoreCase("Yes") && quotedata.get("SpouseDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsSon1Included").equalsIgnoreCase("Yes") && quotedata.get("Son1DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsSon2Included").equalsIgnoreCase("Yes") && quotedata.get("Son2DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsSon3Included").equalsIgnoreCase("Yes") && quotedata.get("Son3DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsDaughter1Included").equalsIgnoreCase("Yes") && quotedata.get("Daughter1DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsDaughter2Included").equalsIgnoreCase("Yes") && quotedata.get("Daughter2DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
	
		if (quotedata.get("IsDaughter3Included").equalsIgnoreCase("Yes") && quotedata.get("Daughter3DOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsFatherIncluded").equalsIgnoreCase("Yes") && quotedata.get("FatherDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsMotherIncluded").equalsIgnoreCase("Yes") && quotedata.get("MotherDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsFatherInLawIncluded").equalsIgnoreCase("Yes") && quotedata.get("FatherInLawDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsMotherInLawIncluded").equalsIgnoreCase("Yes") && quotedata.get("MotherInLawDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsGrandFatherIncluded").equalsIgnoreCase("Yes") && quotedata.get("GrandFatherDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
		if (quotedata.get("IsGrandMotherIncluded").equalsIgnoreCase("Yes") && quotedata.get("GrandMotherDOB") != null) {
			PersonalDetailsAllInsuredDOB.stream().filter(insured -> insured.getText().contains("Self")).findFirst()
			.orElse(null).sendKeys(quotedata.get("SelfDOB"));
		}
	}

}
