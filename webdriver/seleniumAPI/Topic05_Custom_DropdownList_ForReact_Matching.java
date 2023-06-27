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



public class Topic05_Custom_DropdownList_ForReact_Matching {
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
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
	}

	//private List<WebElement> elements;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	long longTimout;

	//PHÍM LÀM ĐẸP CODE: COMMAND + I
	// DÙNG CHO CODE REACT ,Vujs
	//khi trong DOM dropdown không bắt đầu bằng thẻ select và ko có các opiton bên trong thì phải sử dụng dưới dạng custom dropdown


	public void TC_01_SelectCustomDrowdown_ForReact(String parentLocator, String itemLocator, String expectedItem) {
		//Chờ cho tới khi có thể click vào 1 thẻ dropdown
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentLocator)));
		//Click vào 1 thẻ để xổ ra hết tất cả các item trong dropdown
		driver.findElement(By.xpath(parentLocator)).click();

		sleepInSecond(1);

		// Cho trương hợp tìm kiếm matching sau khi tìm dc parentxpath sẽ click và sendkey vào ô text
		String text = "//input[@class='search']";
		driver.findElement(By.xpath(text)).sendKeys("ad");;

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
			// Nếu actual có nhiều ký tự trắng đầu cuối thì thêm hàng trim() để xoá đi rồi mới so sánh
			//Nếu muốn chuyển hoa thường sang 1 loại ký tự thì dùng hàm toLowerCase hoặc toUpCase
			String actualItem = item.getText().toLowerCase();
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
	public void TC_02_searchMatching()
	{

		// Bắt buộc cần tìm tới thằng "//div[@role='option']" chứa tất cả thằng con để duyệt được list
		// trên list hiển thị là Barbados nhưng đã chuyển chuỗi so sánh sang chữ thường hết nên cần để là barbados
		TC_01_SelectCustomDrowdown_ForReact("//input[@class='search']", "//div[@role='option']", "barbados");
		// Nếu là với nghiệp vụ của react nó có thêm atribuile khi item được chọn thì sẽ dùng câu lệnh kiểu dưới
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Barbados']//parent::div")).getAttribute("aria-checked"), "true");
		sleepInSecond(1);

	}



	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
