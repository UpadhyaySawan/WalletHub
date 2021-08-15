package WalletHub;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Facebook {
	
	WebDriver driver;
	String username = " "; 
	String password = " ";
	String status = "Hello World";

	@BeforeTest
	public void beforeTest() throws Exception{
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sawan\\Chrome Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//Disabling Notifications
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void afterTest() {
		
		driver.quit();
	}
	
	@Test
	public void facebookStatusPost() throws Exception{
		
		driver.get("https://www.facebook.com/");
		
		//enter username
		driver.findElement(By.id("email")).sendKeys(username);
		
		//enter password
		driver.findElement(By.id("pass")).sendKeys(password);
		
		//click on Login
		driver.findElement(By.name("login")).click();
		
		//click on post status		
		driver.findElement(By.xpath("//span[contains(text(),\"What's on your mind\")]")).click();
		
		//entering the status
		driver.findElement(By.xpath("//div[@role = \"textbox\"]//br[@data-text = \"true\"]")).sendKeys("Hello World");

		//clicking on Post button
		driver.findElement(By.xpath("//span[text() = \"Post\"]")).click();
		
		//printing to the console
		System.out.println("Status successfully posted!!!");
	}
	

}
