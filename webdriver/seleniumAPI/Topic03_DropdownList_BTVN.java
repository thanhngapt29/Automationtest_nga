package seleniumAPI;
import static org.testng.Assert.assertEquals;

import java.util.List;
// khai báo khi dùng biến ramdom
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//Khai báo khi dùng dropdowlist
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


//Bài tập
//Truy cập Url sau https://demo.nopcommerce.com/register
//Click Register ở header
//Input thông tin hợp lệ vào form, chọn các giá trị trong dropdown
//Day = 1 (kiểm tra trong dropdown có 32 items)
//Month = May (kiểm tra dropdown có 13 items
//Year = 1990 (Kiểm tra trong dropdown có 112 items)
//Click register button
//Verify đã vào trong Homepage sau khi đăng ký thành công
//Thực hiện đăng nhập với account đã đăng ký và kiểm tra ngày tháng năm hiển thị đúng với dữ liệu nhập vào ở bước 3.


public class Topic03_DropdownList_BTVN {
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/ngaphan/Desktop/chromedriver_mac64/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/register");
	}

	public int randomNumber()
	{
		Random rand = new Random();
		return rand.nextInt(9999) + 100000;	
	}

	@Test
	public void TC_01_SlectOptionDrowdown() {
		driver.findElement(By.xpath("//a[@class ='ico-register' and text()='Register']")).click();
		driver.findElement(By.xpath("//input[@id ='gender-male']")).click();
		
		String a = "First name:";
		String b = "Nguyen";
	    String c = 	driver.findElement(By.xpath("//label[text()='First name:']")).getText();
		Assert.assertEquals(c,a);
		driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys(b);
		
		String d = "ABC" + randomNumber();
		driver.findElement(By.xpath("//input[@id='LastName']")).sendKeys(d);
		
		
	    select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
	    Assert.assertEquals(32,select.getOptions().size());
		// Chọn 1 giá trị trong dropdown
		select.selectByVisibleText("1");
		//Kiểm tra giá trị trong dropdown vừa chọn
	    String dayOfDate = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(dayOfDate,"1");
	    
		
	    select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']")));
	    Assert.assertEquals(13,select.getOptions().size());
		// Chọn 1 giá trị trong dropdown
		select.selectByVisibleText("May");
		String monthOfDate = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(monthOfDate,"May");
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']")));
	    Assert.assertEquals(112,select.getOptions().size());
		// Chọn 1 giá trị trong dropdown
		select.selectByVisibleText("1990");
		String yearOfDate = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(yearOfDate,"1990");
		
		String email = "abc" + randomNumber() + "@gmail.com";
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(email);
		
		driver.findElement(By.xpath("//input[@id='Company']")).sendKeys("Company123");
		driver.findElement(By.xpath("//input[@id='Newsletter']")).click();
		String passWord = "Mothaiba123";
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys(passWord);
		driver.findElement(By.xpath("//input[@id='ConfirmPassword']")).sendKeys(passWord);
		driver.findElement(By.xpath("//button[@id='register-button']")).click();
		
		String textSuccess = "Your registration completed";
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Your registration completed')]")).getText(), textSuccess);
		driver.findElement(By.xpath("//a[@class='button-1 register-continue-button']")).click();
		
//		String textHome = "Welcome to our store";
//		Assert.assertEquals(driver.findElement(By.xpath("//h2[text()='Welcome to our store']")).getText(), textHome);
		driver.findElement(By.xpath("//a[text()='My account']")).click();
		
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys(passWord);
		driver.findElement(By.xpath("//button[@class='button-1 login-button']")).click();
		
		String textMyAccount = "My account - Customer info";
		Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='My account - Customer info']")).getText(), textMyAccount);
		
		select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
	    String dayOfDate1 = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(dayOfDate1,dayOfDate);
	    
	    select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']")));
	    String monthOfDate1 = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(monthOfDate1,monthOfDate);
	    
	    select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']")));
	    String yearOfDate1 = select.getFirstSelectedOption().getText();
	    Assert.assertEquals(yearOfDate1,yearOfDate);
		
	    System.out.println("Check success");
		
		
	 
	
	
		
	}
	

	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
