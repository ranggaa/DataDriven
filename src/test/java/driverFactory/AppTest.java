package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest extends FunctionLibrary{
String inputpath ="./FileInput/LoginData.xlsx";
String outputpath ="./FileOutput/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;
@Test
public void startTest() throws Throwable
{
	//define path of html report
	report = new ExtentReports("./target/ExtentReports/Login.html");
	//create object for excel file util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in login sheet
	int rc =xl.rowCount("Login");
	Reporter.log("No of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger = report.startTest("Validate Login");
		logger.assignAuthor("Ranga");
		String username =xl.getCellData("Login", i, 0);
		String password = xl.getCellData("Login", i, 1);
		logger.log(LogStatus.INFO, username+"-----"+password);
		//call login method from functionlibaray class
		boolean res =FunctionLibrary.adminLogin(username, password);
		if(res)
		{
			//if res is true write as valid username and password into results cell
			xl.setCellData("Login", i, 2, "Valid username and password", outputpath);
			//if res is true write as passinto status cell
			xl.setCellData("Login", i, 3, "Pass", outputpath);
			logger.log(LogStatus.PASS, "Username and password are Valid");
		}
		else
		{
			//take screen shot
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screenshot/Iterations/"+i+"Loginpage.png"));
			//if res is false write as invalid username and password into results cell
			xl.setCellData("Login", i, 2, "Invalid username and pasword", outputpath);
			//if res is true write as  fail into status cell
			xl.setCellData("Login", i, 3, "Fail", outputpath);
			logger.log(LogStatus.FAIL, "Username and password are InValid");
			
		}
		report.endTest(logger);
		report.flush();
	}
}
}













