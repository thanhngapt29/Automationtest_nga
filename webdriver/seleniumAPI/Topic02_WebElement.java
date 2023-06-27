package seleniumAPI;
// khai báo khi dùng biến ramdom
import java.util.Random;
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



public class Topic02_WebElement {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/ngaphan/Desktop/chromedriver_mac64/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.techpanda.org/");
	}

	public int randomNumber()
	{
		Random rand = new Random();
		return rand.nextInt(9999) + 100000;
		
	}
	
	@Test
	public void Java_Basic()
	{
		String email = "abcd" + randomNumber()+"@gmail.com";
		System.out.println("Email:" + email);
	}
	

	@Test
	public void TC_01() {
		
//		Truy cập trang http://live.techpanda.org/
//		Click MY ACCOUNT link tại footer
//		Verify url của login Page = http://live.techpanda.org/index.php/customer/account/login/
//		Verify Login page có hiển thị text Login or Create an Account
//		Nhập email invalid sau “ 123@12.123” sau đó click button Login và kiểm tra error message sau hiển thị
//		“Please enter a valid email address. For example johndoe@domain.com.”
//		Click CREATE AN ACCOUNT button
//		Verify Url của register page = http://live.techpanda.org/index.org/index.php/customer/account/create/
//		Verify title của Register page - Create New Customer Account
//		Kiểm tra checkbox Sign Up for Newsletter ko được checked
//		Kiểm tra button REGISTER được enable
//		Nhập valid các thông tin để đăng ký tài khoản thành công
//		Verify sau khi đăng ký thành công thì hiển thị dòng text
//		“Thank you for registering with Main Website Store.” tại trang MY DASHBOARD và có dòng text “Hello, {tên mình đã đăng ký} như ảnh dưới 

		
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		String a = driver.getCurrentUrl();
		Assert.assertEquals(a, "http://live.techpanda.org/index.php/customer/account/login/");
		String b = driver.findElement(By.xpath("//div/h1[text()='                Login or Create an Account            ']")).getText();
		Assert.assertEquals(b, "LOGIN OR CREATE AN ACCOUNT");
		
		String c = "123@12.123";
		driver.findElement(By.xpath("//label[@for='email']/following-sibling::div/input[@type='email']")).sendKeys(c);
		driver.findElement(By.xpath("//button[@type='submit' and @title='Login']")).click();
		
		String d = driver.findElement(By.xpath("//div[text()='Please enter a valid email address. For example johndoe@domain.com.']")).getText();
		Assert.assertEquals(d, "Please enter a valid email address. For example johndoe@domain.com.");
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		String e = driver.getCurrentUrl();
		Assert.assertEquals(e, "http://live.techpanda.org/index.php/customer/account/create/");
		String f = driver.findElement(By.xpath("//h1[text()='Create an Account']")).getText();
		Assert.assertEquals(f, "CREATE AN ACCOUNT");
		
		Assert.assertFalse(driver.findElement((By.xpath("//input[@name='is_subscribed']"))).isSelected());
		Assert.assertTrue(driver.findElement((By.xpath("//button[@title='Register']"))).isEnabled());
		
		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("nguyen van");
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("An");
		
		String email = "abcd" + randomNumber()+"@gmail.com";
		driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Abc123!@");	
		driver.findElement(By.xpath("//input[@id='confirmation']")).sendKeys("Abc123!@");
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		driver.findElement(By.xpath("//strong[contains(text(),'Hello')]")).isDisplayed();
		
	}

	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
