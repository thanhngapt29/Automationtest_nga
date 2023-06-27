package seleniumAPI;
import java.util.List;
// khai báo khi dùng biến ramdom
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
//Khai báo khi dùng dropdowlist
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class Topic04_Custom_DropdownList {
	private static final boolean WebElement = false;
	WebDriver driver;
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");


	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/ngaphan/Desktop/chromedriver_mac64/chromedriver");
		driver = new ChromeDriver();

		// khởi tạo giá trị explicitWait
		explicitWait = new WebDriverWait(driver, longTimout);
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
	}
	
	//private List<WebElement> elements;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	long longTimout;
	
    // DÙNG CHO CODE JQUERY 
	//khi trong DOM dropdown không bắt đầu bằng thẻ select và ko có các opiton bên trong thì phải sử dụng dưới dạng custom dropdown



	public void TC_01_SelectCustomDrowdown(String parentLocator, String itemLocator, String expectedItem) {
		//Chờ cho tới khi có thể click vào 1 thẻ dropdown
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentLocator)));
		//Click vào 1 thẻ để xổ ra hết tất cả các item trong dropdown
		driver.findElement(By.xpath(parentLocator)).click();
		sleepInSecond(1);

		//Chờ cho tất cả các item được có trong HTML DOM
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(itemLocator)));

		//Lấy tất cả các item này đưa vào ListElement, tìm itemLocator là 1 list các item trong dropdown
		List<WebElement> allItems = driver.findElements(By.xpath(itemLocator));
		//Lấy ra tổng số lượng item trong dropdown
		System.out.println("AllItem:" + allItems.size());

		//Duyệt qua từng item
		for (WebElement item : allItems)
		{
			//Mỗi lần duyệt kiểm tra xem text của item đó bằng với item mình click hay không
			String actualItem = item.getText();
			System.out.println(actualItem);
			//Nếu bằng thì sẽ click vào và thoát khỏi không duyệt nữa
			//Nếu không bằng thì duyệt tiếp cho đến hết tất cả item
			if (actualItem.equals(expectedItem))
			{
				//Trước khi click thì nên scroll đến element
				// arguments[0].scrollIntoView(true); support scroll tìm tới giá trị matching với item
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				//Chờ cho tới khi item có thể click được
				explicitWait.until(ExpectedConditions.elementToBeClickable(item));
				item.click();
				break;
			}
		}
	}

	private void sleepInSecond(int i) {
		// TODO Auto-generated method stub

	}

	@Test
	public void TC_02_testdemo()
	{
		TC_01_SelectCustomDrowdown("//span[@id='speed-button']", "//ul[@id='speed-menu']/li", "Slow");
		// chekc giá trị vừa chọn có đúng là hiển thị trên màn hình hay không
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Slow']")).isDisplayed());
		
        TC_01_SelectCustomDrowdown("//span[@id='files-button']", "//ul[@id='files-menu']/li", "Some unknown file");
		TC_01_SelectCustomDrowdown("//span[@id='number-button']", "//ul[@id='number-menu']/li", "16");
		TC_01_SelectCustomDrowdown("//span[@id='salutation-button']", "//ul[@id='salutation-menu']/li", "Prof.");
	}





	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
