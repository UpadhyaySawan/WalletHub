package WalletHub;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestInsuranceReview {
	
	WebDriver driver;
	String email = "upadhyaysawan94@gmail.com";
	String password = "CU6@wUY7L6zdcA9";
	String review = "Test Comments ";
	
	@BeforeTest
	public void beforeTest() throws Exception{
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sawan\\Chrome Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//Disabling Notifications
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void afterTest() {
		
		driver.quit();
	}
	
	@Test
	public void writingTestReview() throws Exception{

		driver.get("http://wallethub.com/profile/test_insurance_company/");
		
		//Logging in
		driver.findElement(By.xpath("//span[text() = \"Login\"]")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//span[text() = \"Login\"]")).click();
		
		//Try catch for Stale Element observed
		try {
			driver.findElement(By.xpath("//button[text() = \"Write a Review\"]")).click();
		}
		catch (StaleElementReferenceException e) {
			System.out.println("StaleElementReferenceException Caught");
			driver.findElement(By.xpath("//button[text() = \"Write a Review\"]")).click();
		}
		
		//Hovering over fourth star
		WebElement star = driver.findElement(By.xpath("((//review-star[@class = \"rvs-svg\"]/div[@class = \"rating-box-wrapper\"]/*[@class = \"rvs-star-svg\"])[9]//*)[2]"));
		Actions act = new Actions(driver);
		act.moveToElement(star).perform();
		System.out.println("Hovered over Fourth star");
		
		//Validating if the star is lit up when hovered over
		String color = driver.findElement(By.xpath("((//review-star[@class = \"rvs-svg\"]/div[@class = \"rating-box-wrapper\"]/*[@class = \"rvs-star-svg\"])[9]//*)[2]")).getAttribute("fill");
		assertEquals(color,"#4ae0e1","Star is not lit up when hovered over");
		System.out.println("Star is lit up when hovered over");

		//Clicking the fourth star
		driver.findElement(By.xpath("((//review-star[@class = \"rvs-svg\"]/div[@class = \"rating-box-wrapper\"]/*[@class = \"rvs-star-svg\"])[9]//*)[2]")).click();
		System.out.println("Clicked on the Fourth star");

		//Selecting the drop down
		driver.findElement(By.xpath("//span[contains(text(), \"Select\")]")).click();
		driver.findElement(By.xpath("//li[text() = \"Health Insurance\"]")).click();
		System.out.println("Dropdown selected");
		
		//Writing the Review
		while(review.length() < 200) {
			review = review + review;
		}
		WebElement writeReview = driver.findElement(By.xpath("//textarea[@placeholder = \"Write your review...\"]"));
		writeReview.clear();
		writeReview.sendKeys(review);
		System.out.println("Review Written");
		
		//clicking on Submit button
		driver.findElement(By.xpath("//div[contains(text(), \"Submit\")]")).click();
		System.out.println("Review Submitted");
		
		//clicking on Continue button
		driver.findElement(By.xpath("//div[contains(text(), \"Continue\")]")).click();

		//Going to Profile
		WebElement profileName = driver.findElement(By.xpath("//div[@id = \"more-list-menu\"]/following::span[@class = \"brgm-list-title\"]"));
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.visibilityOf(profileName));
		act.moveToElement(profileName).perform();
		System.out.println("Hovered over Profile");
		driver.findElement(By.xpath("//div[@class = \"brgm-list brgm-user-list ng-enter-element\"]//a[text() = \"Profile\"]")).click();
		System.out.println("Navigated to Profile");
		
		//Asserting whether we can see the review
		assertTrue(driver.getPageSource().contains("I RECOMMEND"), "Review Not Found!!! Assertion Fail");
		System.out.println("Review Found & Test is Passed!!!");
		
	}

}
