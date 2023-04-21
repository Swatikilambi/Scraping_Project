package utilities;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

	public WebDriver driver;
	
	
	@BeforeTest
	public void openBrowser() {
				
			ChromeOptions ops = new ChromeOptions();
			
			WebDriverManager.chromedriver().setup();
			
			ops.addArguments("--remote-allow-origins=*");
			// this will make sure that alters does not close automatically 
			//and allow us to handle alert in code.
			ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
			driver = new ChromeDriver(ops);
				
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		
		
	}
	
	
	//@AfterTest
	public void closeDriver() {
		
		driver.quit();
	}
}


