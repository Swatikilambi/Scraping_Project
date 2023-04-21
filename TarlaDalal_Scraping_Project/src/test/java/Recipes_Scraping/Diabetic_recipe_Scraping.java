package Recipes_Scraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import utilities.Browser;

public class Diabetic_recipe_Scraping extends Browser{
	String ingd;
	int cre,i;
	String recid;
	WebElement rc;
	String url= "https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=1";

	@Test(priority=1)
	public void openurl() throws InterruptedException {
		
		driver.get(url);
		
		
	}
	@Test(priority=2)
	public void clickReciep() throws InterruptedException
	{
		
		
		//System.out.println("pagecount"+driver.findElements(By.xpath("//div[@id='pagination']")).size());
		int size= driver.findElements(By.xpath("//span[@class='rcc_recipename']")).size();
		String targetmorbidcondition= driver.findElement(By.xpath("//span[@id='ctl00_cntleftpanel_lblSearchTerm']//span//h1")).getText();
		System.out.println("Target Morbid Condition :"+targetmorbidcondition);
		//for(i=2;i<=2;i++) {
			for(cre=1;cre<=size;cre++)
			{
			rc=driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+cre+"]//a[@itemprop='url']"));
			recid=driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+cre+"]//div[@class='rcc_rcpno']//span[contains(text(),'Recipe')]")).getText();
			String rcname=rc.getText();
			System.out.println("==============================================");
			System.out.println("Recipe ID: "+recid);
			System.out.println("Recipe Name: "+rcname);
			System.out.println("==============================================");
			rc.click();
			checkelimination();
			//reciepDetails();
			driver.navigate().back();
			}
		//WebElement pg= driver.findElement(By.xpath("//a[text()='"+i+"']"));
		//pg.click();
		//}
	}
	public boolean checkelimination()
	{
		ingd= driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
		if(ingd.contains("oats")==true)
		{
			return true;
		}
		else {
			
			reciepDetails();
			return false;
		}
		
	}
	//@Test(priority=2)
	public void reciepDetails()
	{
		System.out.println("Ingredients:");
		System.out.println(ingd);
		System.out.println("Preperation Time: "+ driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText());
		System.out.println("Cooking Time: "+ driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText());
		System.out.println("Preperation Method: " );
		System.out.println(driver.findElement(By.xpath("//div[@id='recipe_small_steps']//ol[@itemprop='recipeInstructions']")).getText());
		System.out.println(driver.findElement(By.xpath("//span[@itemprop='nutrition']")).getText());
		System.out.println("Recipe URL: "+ driver.getCurrentUrl());	
	}

}
