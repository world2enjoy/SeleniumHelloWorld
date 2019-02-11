package test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainTest {

	static Logger log = Logger.getLogger(MainTest.class.getName());
	private static final String GOOGLE_URL = "https://www.google.com";
	private String os = null;

	public static void main(String[] args) {
		MainTest main = new MainTest();
		main.setFirefoxDriverProperty();

		try {
			main.googleSearch1();
			main.googleSearch2();
		} catch (InterruptedException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public String getOsName() {
		if (os == null) {
			os = System.getProperty("os.name");
		}
		return os;
	}

	private boolean isWindows() {
		return getOsName().startsWith("Windows");
	}

	private boolean isLinux() {
		return getOsName().startsWith("Linux");
	}

	private boolean isOSX() {
		return getOsName().startsWith("Windows");
	}

	/**
	 * Set FirefoxDriver Property
	 * 
	 * @see https://github.com/mozilla/geckodriver/releases for geckodriver
	 */
	public void setFirefoxDriverProperty() {
		if (isWindows()) {
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		}
		if (isLinux()) {
			// TODO set FirefoxDriver Property for Linux
		}
		if (isOSX()) {
			// TODO set FirefoxDriver Property for OSX
		}
	}

	public void googleSearch1() throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.get(GOOGLE_URL);
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!\n"); // send also a "\n"
		element.submit();

		Thread.sleep(2000);

		List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

		// this are all the links you like to visit
		for (WebElement webElement : findElements) {
			log.log(Level.INFO, webElement.getAttribute("href"));
		}

		// Get the url of third link and navigate to it
		String thirdLink = findElements.get(2).getAttribute("href");
		driver.navigate().to(thirdLink);

		Thread.sleep(2000);

		driver.quit();
	}

	public void googleSearch2() throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.get(GOOGLE_URL);
		driver.manage().window().maximize();

		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!\n");
		element.submit();

		// Wait until the google page shows the result
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
		
		log.log(Level.INFO, String.format("resultStats: %s", myDynamicElement.toString()));

		List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

		// Get the url of third link and navigate to it
		String thirdLink = findElements.get(2).getAttribute("href");
		driver.navigate().to(thirdLink);

		Thread.sleep(2000);

		driver.quit();

	}
}
