package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class FunctionLibrary extends AppUtil {
public static boolean adminLogin(String user,String pass) throws Throwable
{
	driver.get(conpro.getProperty("Url"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.xpath(conpro.getProperty("ObjReset"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Objuser"))).sendKeys(user);
	driver.findElement(By.xpath(conpro.getProperty("Objpass"))).sendKeys(pass);
	driver.findElement(By.xpath(conpro.getProperty("ObjLogin"))).click();
	Thread.sleep(2000);
	String Expected ="dashboard";
	String Actual = driver.getCurrentUrl();
	if(Actual.contains(Expected))
	{
		Reporter.log("Username and password valid::"+Expected+"    "+Actual,true);
		driver.findElement(By.xpath(conpro.getProperty("ObjLogout"))).click();
		return true;
		
	}
	else
	{
		//capture error message
		String message =driver.findElement(By.xpath(conpro.getProperty("ObjErrorMessage"))).getText();
		Thread.sleep(2000);
		driver.findElement(By.xpath(conpro.getProperty("ObjOkbutton"))).click();
		Reporter.log(message+"    "+Expected+"      "+Actual,true);
		return false;
	}
	
	
}
}
