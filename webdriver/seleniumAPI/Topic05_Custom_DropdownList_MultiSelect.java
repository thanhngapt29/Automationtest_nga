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



public class Topic05_Custom_DropdownList_MultiSelect {
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
		driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
	}

	//private List<WebElement> elements;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	long longTimout;

	// DÙNG CHO CODE MULTISELECT
	//khi trong DOM dropdown không bắt đầu bằng thẻ select và ko có các opiton bên trong thì phải sử dụng dưới dạng custom dropdown

	public void SelectCustomDrowdown_ForMultiSelect(String parentXpath, String childXpath, String[] expectedItem) {
		//1. Chờ cho tới khi có thể click vào 1 thẻ dropdown
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentXpath)));
		//2. Click vào 1 thẻ để xổ ra hết tất cả các item trong dropdown
		driver.findElement(By.xpath(parentXpath)).click();
		sleepInSecond(1);

		//Chờ cho tất cả các item được có trong HTML DOM có thể loaid ra
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		//Lấy tất cả các item này đưa vào ListElement, tìm childXpath là 1 list các item trong dropdown
		List<WebElement> allItems = driver.findElements(By.xpath(childXpath));
		//Lấy ra tổng số lượng item trong dropdown
		System.out.println("AllItem:" + allItems.size());

		//Duyệt qua từng item cho tới khi thoả mãn
		for (WebElement childElement : allItems)
		{
			// "January", "April", "July"
			for(String item: expectedItem)
			{
				if(childElement.getText().equals(item))
				{
					//Scroll tới item cần chọn, nếu như nhìn thấy luôn thì không cần scrool
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					sleepInSecond(1);

					//Click vào item cần chọn
					childElement.click();
					sleepInSecond(1);
					List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.print("Item Selected=" + itemSelected.size());
					if(expectedItem.length == itemSelected.size())
					{
						break;
					}

				}

			}
		}
	}

	//Kiểm tra các giá trị đã chọn đúng hay sai
	public boolean areItemSelected(String[] months) {
		//List các item đã được select
		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		//numberItemSelected là tổng số lượng item đã select
		int numberItemSelected = itemSelected.size();

		//get text của các item đã select
		String allItemSelectedText = driver.findElement(By.xpath("(//button[@class='ms-choice']/span)[1]")).getText();
		System.out.println("Text da chon = " + allItemSelectedText);
		// Nếu item select <=3 thì hiển thị text của các item cách nhau bằng dấu phẩy
		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			boolean status = true;
			for (String item : months) {
				if (!allItemSelectedText.contains(item)) {
					status = false;
					return status;
				}
			}
			return status;
			//Nếu số lượng item >=12 thì hiển thị text All selected 
		} else if (numberItemSelected >= 12) {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='All selected']")).isDisplayed();
			//Nếu số lượng item mà >3 và <12 thì sẽ hiển thị number + of 12 selected
		} else if (numberItemSelected > 3 && numberItemSelected < 12) {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']")).isDisplayed();
		} else {
			return false;
		}
	}

	private void sleepInSecond(int i) {
		// TODO Auto-generated method stub

	}
	@Test
	public void TC_02_MultipleDropdown() {
		String[] expectedmotnh = {"January", "April", "July"};
		SelectCustomDrowdown_ForMultiSelect("(//div[@class='ms-parent multiple-select '])[1]", "(//div[@class='ms-parent multiple-select ms-parent-open'])[1]//span", expectedmotnh);
		areItemSelected(expectedmotnh);
		// So sánh giá trị chọn trên màn hình với expectedmotnh
		Assert.assertTrue(areItemSelected(expectedmotnh));
		
//		selectMultiItemInDropdown("(//button[@type='button'])[1]","//div[@class = 'ms-drop bottom']/ul/li", expect);
//
//		String t = driver.findElement(By.xpath("(//button[@class='ms-choice']/span)[1]")).getText();
//
//		    Assert.assertEquals(t,"January, April, July");
	}
	




	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
