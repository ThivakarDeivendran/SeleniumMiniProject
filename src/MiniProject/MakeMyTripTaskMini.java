package MiniProject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MakeMyTripTaskMini {
	static WebDriver hotelbooking;
	static String checkInDate,checkOutDate;
	static int totalNoOFDays =3;
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\User\\Document\\Drivers\\chromedriver.exe");
		hotelbooking= new ChromeDriver();
		hotelbooking.manage().window().maximize();
		hotelbooking.manage().deleteAllCookies();
		hotelbooking.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		hotelbooking.navigate().to("https://www.makemytrip.com/");
		Thread.sleep(3000);
		hotelbooking.findElement(By.xpath("//ul[@class='makeFlex font12']/li[2]")).click();
		Thread.sleep(1000);
		hotelbooking.findElement(By.xpath("//*[@id='city']")).click();
		Thread.sleep(1000);
		hotelbooking.findElement(By.xpath("//input[@type='text'][@autocomplete='nope']")).sendKeys("GOA");
		Thread.sleep(3000);
		Actions firstCityName = new Actions(hotelbooking);
		firstCityName.moveToElement(hotelbooking.findElement(By.xpath("//div[@id='react-autowhatever-1']/div/ul/li[1]"))).click().build().perform();

		/*List<WebElement> selectCheckInDate = hotelbooking.findElements(By.xpath("//*[@class='dateFiled']/p/span[2]/span"));
		for (WebElement checkInDate : selectCheckInDate) {
			System.out.println(checkInDate.getText());
		}*/	
		List<WebElement> startDate = hotelbooking.findElements(By.xpath("//div[@class='DayPicker-Months']/div/div/div/div[@aria-disabled='false']"));
		for (int i=0;i<startDate.size();i++) {
			checkInDate=startDate.get(0).getText();
			checkOutDate =startDate.get((totalNoOFDays)-1).getText();
			if(startDate.get(i).getText().equals(checkInDate)) {
				startDate.get(i).click();
			}
			if(startDate.get(i).getText().equals(checkOutDate)) {
				startDate.get(i).click();
				break;
			}
		}
		WebElement  roomGuest =hotelbooking.findElement(By.xpath("//div[@class='rmsGst__body']/div[2]/div[2]"));
		roomGuest.click();
		Actions AdultGuest = new Actions(hotelbooking);
		AdultGuest.moveToElement(hotelbooking.findElement(By.xpath("//div[@class='rmsGst__body']/div[2]/div[2]/ul/li"))).click().build().perform();
		hotelbooking.findElement(By.xpath("//button[text()='Apply']")).click();
		Thread.sleep(2000);
		hotelbooking.findElement(By.xpath("//button[text()='Search']")).click();
		Thread.sleep(5000);
		hotelbooking.findElement(By.xpath("(//*[@type='text'])[5]")).sendKeys("Resort Rio");
		Thread.sleep(2000);
		Actions hotelResort = new Actions(hotelbooking);
		hotelResort.moveToElement(hotelbooking.findElement(By.xpath("(//*[@id='react-autowhatever-1--item-0'])[1]"))).click().build().perform();
		Thread.sleep(3000);
		hotelbooking.findElement(By.xpath("//button[text()='Search']")).click();
		Thread.sleep(2000);
		List<WebElement> searchHotel = hotelbooking.findElements(By.xpath("//div[@class='flexOne makeFlex']"));
		for(int i=0;i<searchHotel.size();i++) {
			searchHotel.get(0).click();
			break;
		}
		String makeMyTrip = hotelbooking.getWindowHandle();
		Set<String> totalwindowhandles = hotelbooking.getWindowHandles();
		Iterator<String> iterator = totalwindowhandles.iterator();
		while(iterator.hasNext()) {
			String hotelDetailPage =iterator.next();
			if(!makeMyTrip.equalsIgnoreCase(hotelDetailPage)) {
				hotelbooking.switchTo().window(hotelDetailPage);
			}
		}
		//JavascriptExecutor scrolToBooking =(JavascriptExecutor) hotelbooking;
		//scrolToBooking.executeScript("window.scrollBy(0,200)", "");
		System.out.println(hotelbooking.getCurrentUrl());
		Thread.sleep(3000);
		hotelbooking.findElement(By.id("detpg_headerright_book_now")).click();
		System.out.println(hotelbooking.getCurrentUrl());

		//scrolToBooking.executeScript("window.scrollBy(0,600)", "");
	}
	static void screenShotMethod() throws IOException  {
		TakesScreenshot screenshot = (TakesScreenshot) hotelbooking;
		File srcfile =screenshot.getScreenshotAs(OutputType.FILE);
		String filename ="Screenshot"+ System.currentTimeMillis() +".png";
		File destinationfile= new File("D:\\User\\Document\\Eclipse_workspace\\SeleniumMiniProject\\ScreenShotFolder\\"+filename);
		FileUtils.copyFile(srcfile, destinationfile);
	}
}
