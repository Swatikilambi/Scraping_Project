package Recipes_Scraping;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import utilities.Browser;
import utilities.Excel_utilities;

public class PCOS_Recipe_Scraping extends Browser
{
	String ingd;
	int cre,i;
	String recid,rcname;
	WebElement rc;
	String targetmorbidcondition;
	String url= "https://www.tarladalal.com/recipes-for-pcos-1040";
	int size;
	
	@Test(priority=1)
	public void openurl() throws InterruptedException {
		
		driver.get(url);
		
		
	}
	@Test(priority=2)
	public void clickReciep() throws InterruptedException
	{
		
		int pagecount=driver.findElements(By.xpath("//div[@id='pagination']/a")).size();
		size= driver.findElements(By.xpath("//span[@class='rcc_recipename']")).size();
		System.out.println("recipe size"+size);
		targetmorbidcondition= driver.findElement(By.xpath("//span[@id='ctl00_cntleftpanel_lblSearchTerm']//span//h1")).getText();
		System.out.println("Target Morbid Condition :"+targetmorbidcondition);
		for(i=2;i<=pagecount ;i++) {
			for(cre=1;cre<=size;cre++)
			{
			rc=driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+cre+"]//a[@itemprop='url']"));
			recid=driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+cre+"]//div[@class='rcc_rcpno']//span[contains(text(),'Recipe')]")).getText();
			int recidSize=driver.findElements(By.xpath("//article[@class='rcc_recipecard'][\"+cre+\"]//div[@class='rcc_rcpno']//span[contains(text(),'Recipe')]")).size();
			rcname=rc.getText();
			System.out.println("Recid size"+recidSize);
			rc.click();
			checkElimination();
			//reciepDetails();
			driver.navigate().back();
			}
		WebElement pg= driver.findElement(By.xpath("//a[text()='"+i+"']"));
		pg.click();
		}
	}
	
	public void checkElimination()
	{
	
		String  projectpath = System.getProperty("user.dir"); 
		Excel_utilities reader= new Excel_utilities(projectpath+"/src/test/resources/PCOS_Eliminate_Ingredient_List.xlsx","PCOS_Eliminate_Ingredients");
		int rowCount= reader.GetRowCount(); 
		int colcount= reader.GetColumnCount();
		ingd= driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText().replace("\n", " ");
		
		String[] ingdArray=ingd.split(" ");
		List<String> ingdList= Arrays.asList(ingdArray);
		int colNum=0;
		List<String> EliminateIngredients=new ArrayList<>();
		for(int rowNum=1; rowNum<rowCount; rowNum++){
			EliminateIngredients.add(reader.getCellData(rowNum, colNum));
		}
		
	 Set<String> dupList=  EliminateIngredients.stream().filter(a -> ingdList.contains(a)).collect(Collectors.toSet());
		
		if(dupList.size()==0)
		{
			
			reciepDetails();
		}else {
			System.out.println("***************************************************************************");
			System.out.println("Eliminated ingredient found/This recipe not suitable for PCOS :"+Arrays.toString(dupList.toArray()));
			System.out.println("***************************************************************************");
		}
		}
	
		
	//@Test(priority=2)
	public void reciepDetails()
	{
		String  projectpath = System.getProperty("user.dir"); 
		Excel_utilities recipedetailsreader= new Excel_utilities(projectpath+"/src/test/resources/PCOS_Eliminated_Recipe_Details.xlsx","PCOS_Eliminated_Recipe_Data");
		int Writerowcount= recipedetailsreader.GetRowCount();
		int writecolCount=recipedetailsreader.GetColumnCount();
		System.out.println("Writeexcel row count"+Writerowcount);
		System.out.println("Writeexcel col count"+writecolCount);
		
		String preptime=driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
		String cooktime=driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
		String PrepMethod= driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol[@itemprop='recipeInstructions']")).getText();
		String NitriValue= driver.findElement(By.xpath("//span[@itemprop='nutrition']")).getText();
		String RecURL= driver.getCurrentUrl();
		
		for(int we=1;we<=size;we++)
		{
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", recid, 2, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", rcname, 3, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", "Lunch,Snack,Dinner", 4,we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", "Veg", 5, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", ingd, 6, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", preptime, 7, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", cooktime, 8, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", PrepMethod, 9,we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", NitriValue, 10, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", "PCOS", 11, we);
		recipedetailsreader.setCellData("PCOS_Eliminated_Recipe_Data", RecURL, 12, we);
		}
		System.out.println("==============================================");
		System.out.println("Recipe ID: "+recid);
		System.out.println("Recipe Name: "+rcname);
		System.out.println("==============================================");
		System.out.println("Ingredients:");
		System.out.println(ingd);
		System.out.println("Preperation Time: "+preptime );
		System.out.println("Cooking Time: "+ cooktime);
		System.out.println("Preperation Method: " );
		System.out.println(PrepMethod);
		System.out.println(NitriValue);
		System.out.println("Recipe URL: "+RecURL);	
	}
	
	
	
	
	
	
	
	
	
	/*ArrayList<String> PCOSEliminateList = new ArrayList<>(Arrays.asList("Cakes","Pastries","White bread","Fried food","Pizza",
			"Burger","Carbonated beverages","Sugary foods (sweets, icecreams)","beverages (soda, juices)","Red meat",
			"Processed meat","Dairy","Soy products","Gluten","Pasta","White rice","Doughnuts","Fries",
			"Coffee","Seed oils- vegetable oil, soybean oil, canola oil, rapeseed oil, sunflower oil, safflower oil"));*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//@Test(priority=2)
	/*public void ingrdientList()
	{
		int numOfIngredients=driver.findElements(By.xpath("//div[@id='recipe_ingredients']//span//a")).size();
		System.out.println("Number of ingredients: "+numOfIngredients);
		//ArrayList<String> list = new ArrayList<String>();
		for(int j=2;j<=numOfIngredients;j++)
		{
			String str= driver.findElement(By.xpath("//div[@id='recipe_ingredients']//span["+j+"]")).getText();
			System.out.println(str);
			//list.add(j, str);
			//System.out.println("Ingredient List: "+list);
			//div[@id='recipe_ingredients']//span[2]//a
			
	}
		//System.out.println("pagecount"+driver.findElements(By.xpath("//div[@id='pagination']")).size());
	}*/
		
}
