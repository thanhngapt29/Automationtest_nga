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



public class Topic06_Baitap {
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
		driver.get("https://ccr.dev-freekey.hitseries.com/login");
	}

	//private List<WebElement> elements;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	long longTimout;

	public By byxpath(String xpathValue) {
        return By.xpath(xpathValue);
    }
	// hàm scrollToElement
    public void scrollToElement(WebDriver driver, String xpathValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", find(driver, xpathValue));
    }
    public WebElement find(WebDriver driver, String xpathValue) {
        return driver.findElement(byxpath(xpathValue));
    }
	
	//Khai báo màn login
	public void Login(String userName,String passWord) {
		driver.findElement(By.xpath("//label[text()='ログインID']/following-sibling::input[@required='required']")).click();
		driver.findElement(By.xpath("//label[text()='ログインID']/following-sibling::input[@required='required']")).sendKeys(userName);
		driver.findElement(By.xpath("//label[text()='パスワード']/following-sibling::input[@required='required']")).sendKeys(passWord);
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()=' パスワードをお忘れの方 ']")).getText(), "パスワードをお忘れの方");
		driver.findElement(By.xpath("//button[@type='button']/span[text()='ログイン']")).click();
		sleepInSecond(2);
	}
	
	
	//Khai báo click Menu và button + 車両を登録
	public void Menu(String menu, String buttonPlus) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(menu)));
		driver.findElement(By.xpath(menu)).click();
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonPlus)));
		driver.findElement(By.xpath(buttonPlus)).click();
		sleepInSecond(2);
	}
	
	public void SelectCustomDrowdown(String parentLocator, String itemLocator, String expectedItem) {
		// KHởi tạo explicitWait
		explicitWait = new WebDriverWait(driver, longTimout);
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
	
	//Khai báo tạo xe mới
	public void CreateVehicle(String vehicleName, String vehicleNumber,String Boarding, String maxCapacity, String fileUpload,
			String dateReservationFrom, String dateReservationTo, String customerVehicleID,String reservationDay, 
			String numberofDay,String selectboxParking, String pakingPosition, 
			String checkboxVehicle, String remarks, String freeItem1, String freeItem2) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleName)));
		String expectVehicleName = "VehicleName2706";
		String expectVehicleNumber = "123456789";
		String expectBoarding = "12";
		String expectmaxCapacity = "15";
		String expectfileUpload = "/Users/ngaphan/Downloads/download.jpeg";
		String parentLocator_DigitalKey = "//div[text()=' デジタルキー ']/following-sibling::span//input[@autocomplete='off']";
		String itemLocator_DigitalKey = "//div[@role='option']/div/div";
		String expectedItem_DigitalKey = "非搭載";
		String parentLocator_Ownership = "//div[text()=' 所有区分 ']/following-sibling::span//input[@autocomplete='off']";
		String itemLocator_Ownership = "//div[@role='option']/div/div";
		String expectedItem_Ownership = "リース";
		String expect_dateReservationFrom = "2023-06-23";
		String expect_dateReservationTo = "2023-07-23";
		String expectedcustomerVehicleID = "9999991";
		String parentLocator_Department = "//div[text()=' 部署 ']/following-sibling::span//input[@required='required']";
		String itemLocator_Department = "//div[@role='option']/div/div";
		String expectedItem_Department = "Tanaakkテスト部署００４";
		String parentLocator_vehicleUser = "//div[text()=' 車両利用者ルール ']/following-sibling::span//input[1]";
		String itemLocator_vehicleUser = "//div[@role='option']/div/div";
		String expectedItem_vehicleUser = "Rule tổng";
		String parentLocator_vehicleClassification = "//div[text()='車両分類']/following-sibling::span//input[1]";
		String itemLocator_vehicleClassification = "//div[@role='option']/div/div";
		String expectedItem_vehicleClassification = "Sedan";
		String parentLocator_status = "//div[text()=' ステータス ']/following-sibling::span//input[1]";
		String itemLocator_status = "//div[@role='option']/div/div";
		String expectedItem_status = "故障確認中";
		String expectedreservationDay="2";
		String expectnumberofDay = "2";
		String parentLocator_reservableDate = "//div[text()=' 予約可能日 ']/following-sibling::span//input[1]";
		String itemLocator_reservableDate = "//div[@role='option']/div/div";
		String expectedItem_reservableDate = "全日";
		String parentLocator_dropdownParking = "//div[text()=' 駐車場 ']/following-sibling::div//input[@type='text']";
		String itemLocator_dropdownParking = "//div[@role='option']";
		String expectedItem_dropdownParking = " Parking lot 02 ";
		String expectedpakingPosition = "HaNoi";
		String parentLocator_driver = "//div[text()=' 運転者 ']/following-sibling::span//input[@type='text']";
		String itemLocator_driver = "//div[@role='option']";
		String expectedItem_driver = "DEV_Nghi   tanaakk.ccr+Nghi@gmail.com";
		String expectedremarks = "1243434";
		
		String expectfreeItem1 = "23";
		String expectfreeItem2 = "34";
		String expectAlert ="無効な個体識別コードです";
		
		
		driver.findElement(By.xpath(vehicleName)).sendKeys(expectVehicleName);
		driver.findElement(By.xpath(vehicleNumber)).sendKeys(expectVehicleNumber);
		driver.findElement(By.xpath(Boarding)).sendKeys(expectBoarding);
		driver.findElement(By.xpath(maxCapacity)).sendKeys(expectmaxCapacity);
		driver.findElement(By.xpath(fileUpload)).sendKeys(expectfileUpload);
		SelectCustomDrowdown(parentLocator_DigitalKey, itemLocator_DigitalKey, expectedItem_DigitalKey);
		SelectCustomDrowdown(parentLocator_Ownership, itemLocator_Ownership, expectedItem_Ownership);
		//Chọn Datepicker yyyy-mm-dd
		//Xoá thuộc tính read-only của ô date và truyền vào date 2023-06-23
	    WebElement elementName1 = driver.findElement(By.xpath(dateReservationFrom));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", elementName1);
        elementName1.sendKeys(expect_dateReservationFrom);
        WebElement elementName2 = driver.findElement(By.xpath(dateReservationTo));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", elementName2);
        elementName2.sendKeys(expect_dateReservationTo);
        // dùng để scroll tới customerVehicleID
        scrollToElement(driver, customerVehicleID);
        driver.findElement(By.xpath(customerVehicleID)).sendKeys(expectedcustomerVehicleID);
        SelectCustomDrowdown(parentLocator_Department, itemLocator_Department, expectedItem_Department);
        SelectCustomDrowdown(parentLocator_vehicleUser, itemLocator_vehicleUser, expectedItem_vehicleUser);
        SelectCustomDrowdown(parentLocator_vehicleClassification, itemLocator_vehicleClassification, expectedItem_vehicleClassification);
        SelectCustomDrowdown(parentLocator_status, itemLocator_status, expectedItem_status);
        driver.findElement(By.xpath(reservationDay)).sendKeys(expectedreservationDay);
        driver.findElement(By.xpath(numberofDay)).sendKeys(expectnumberofDay);
        SelectCustomDrowdown(parentLocator_reservableDate, itemLocator_reservableDate, expectedItem_reservableDate);
        driver.findElement(By.xpath(selectboxParking)).click();
        SelectCustomDrowdown(parentLocator_dropdownParking, itemLocator_dropdownParking, expectedItem_dropdownParking); 
        driver.findElement(By.xpath(pakingPosition)).sendKeys(expectedpakingPosition);
        driver.findElement(By.xpath(checkboxVehicle)).click();  
        SelectCustomDrowdown(parentLocator_driver, itemLocator_driver, expectedItem_driver); 
        driver.findElement(By.xpath(remarks)).sendKeys(expectedremarks);
        driver.findElement(By.xpath(freeItem1)).sendKeys(expectfreeItem1);
        driver.findElement(By.xpath(freeItem2)).sendKeys(expectfreeItem2);
       
        driver.findElement(By.xpath("//button[@type='button']//span[text()='車両を登録']")).click();
        driver.findElement(By.xpath("//button[@type='button']//span[text()=' 登録する ']")).click();
        explicitWait.until(ExpectedConditions.alertIsPresent());
        
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='無効な個体識別コードです']")).getText(), expectAlert);
         
        
        sleepInSecond(2);
	

	
	}


	private void sleepInSecond(int i) {
		// TODO Auto-generated method stub

	}
	@Test
	public void TC_02_Execute() {
		Login("hacconuong260991+2@gmail.com","Nguyen123");	
		Menu("//div[text()='車両']", "//span[text()='車両を登録']//parent::div");
		CreateVehicle("//div[text()=' 車両名 ']/following-sibling::span//input[@required='required']",
				"//input[@placeholder='品川500 あ 1234']",
				"//div[text()='乗車定員']/following-sibling::span//input[@required='required']",
				"//div[text()='kg']/preceding-sibling::input",
				"//input[@accept='image/jpeg,image/png']",
				"//input[@placeholder='開始日']",
				"//input[@placeholder='終了日']",
				"//div[text()='お客様車両ID']/following-sibling::span//input",
				"//div[text()='日後まで']/preceding-sibling::input",
				"//div[text()='日']/preceding-sibling::input",
				"//div[@class='v-input--selection-controls__ripple primary--text']",
				"(//div[@class='v-input--selection-controls__ripple'])[2]",
				"//div[text()='駐車位置']/following-sibling::span//input[@type='text']",
				"//div[text()='備考']/following-sibling::span//textarea",
				"//div[text()='自由項目1']/following-sibling::span//input",
				 "//div[text()='自由項目2']/following-sibling::span//input");


	}

	//@Test
	public void TC_03_Execute() {
		Login("hacconuong260991+2@gmail.com","Nguyen123");	
		Menu("//div[text()='車両']", "//span[text()='車両を登録']//parent::div");
		SelectCustomDrowdown("//div[text()=' デジタルキー ']/following-sibling::span//input[@autocomplete='off']", "//div[@role='option']/div/div", "非搭載");


	}




	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
