package seleniumAPI;
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



public class Topic03_DropdownList2 {
	WebDriver driver;
	//khai báo select để dùng dropdown
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/ngaphan/Desktop/chromedriver_mac64/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://egov.danang.gov.vn/reg");
	}

	// Thẻ bắt đầu bằng select và có các option bên trong nhìn thấy trên DOM thì là dropdow mặc định nên có thể sử dụng hàm select sẵn của selenium
	

	@Test
	public void TC_01_SlectOptionDrowdown() {
		
		select = new Select(driver.findElement(By.xpath("//select[@id=\"thuongtru_tinhthanh\"]")));
		// Chọn 1 giá trị trong dropdown
		select.selectByVisibleText("thành phố Hà Nội");
		//sleepInSecond(2);
		
		//Kiểm tra giá trị trong dropdown vừa chọn
	 String a = select.getFirstSelectedOption().getText();
	 Assert.assertEquals(a,"thành phố Hà Nội");
	 
	 // Tổng số lượng tỉnh thành có trong dropdown
	 int b = select.getOptions().size();
	 System.out.println("Số lượng tỉnh thành:" + b);
	 
	 //
	 List<WebElement> tinhValue = select.getOptions();
	 for (int i =0; i < tinhValue.size(); i++)
	 {
		 System.out.println("Tỉnh:" + tinhValue.get(i).getText());
	 }
	 
		
	}
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
