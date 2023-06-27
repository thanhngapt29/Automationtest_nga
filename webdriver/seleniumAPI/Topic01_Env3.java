package seleniumAPI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class Topic01_Env3 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/ngaphan/Desktop/Automation/Mac OS/Workspace/Test/browersDrivers/chromedriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/");
	}

	

	@Test
	public void TC_01() {
		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys("test01@gmail.com");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
		String a = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		String b = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
		
		driver.findElement(By.xpath("//a[text()='Agile Project']")).click();
		
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(a);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(b);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
	 
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
