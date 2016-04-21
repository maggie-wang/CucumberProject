package se.iths.CucumberProject;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * Author: YU WANG
 * Date: 2016-04-07
 * 
 * White-box testing: test UI component 
 * Branch testing. (test cases: buyOneItemCheckOutWithOutLogin and buyOneItemContinueWithOutoginIn)
 * Use case testing
 * Boundary testing --- checkOutWithEmptyShoppingCartWiutoginInCheckCounter
 * Test cases cover different senarios/flows.
 * Most test cases here are assumed that user is not logged in the system.
 * The only test cases that need login info is when test the whole purchase process.
 * 
 */
public class POM_ShoppingCart_Test {

	private WebDriver driver;
	String userName = "cat";
	String pwd = "be123456";
	String itemName;
	double itemPrice;
	String popUpCheckOutBtnLabel = "go_to_checkout";
	

	@Before
	public void setup(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://store.demoqa.com");
		driver.manage().window().maximize();

	}
	
	@After
	public void testDown(){
		driver.quit();
	}
	
	// Buy one item and continue shopping---without log in. 
    @Test
    public void buyOneItemContinueWithOutoginIn() throws InterruptedException{
    	clickBuyNow_btn();
    	addOneItemInShoppingCart();
    	AlertPopupPage.popup_Btn("continue_shopping", driver).click();
    	Thread.sleep(2000);	    	  	
    } 
    
	// //Buy one item and continue shopping---without log in. 
    @Test
    public void buyOneItemForTenTimesContinueWithOutLoginIn() throws InterruptedException{
    	
    	int originItemCounter = HomePage.getItemNumber(driver);
    	HomePage.clickBuyNow_btn(driver);
    	
    	for(int i = 0; i < 10; i++){
    		addOneItemInShoppingCart();
    		AlertPopupPage.popup_Btn("continue_shopping", driver).click(); 
    		Thread.sleep(5000);
    	}
    	int changedItemCounter = HomePage.getItemNumber(driver);
    	assertEquals(10, (changedItemCounter - originItemCounter));
    	
    }    
	
   // test counter of shopping cart  ---without log in. 
   @Test
   public void buyOneItemCheckOutWithoutLoginInCheckCounter() throws InterruptedException{
   	
   	int originItemCounter = HomePage.getItemNumber(driver);
   	
   	
   	clickBuyNow_btn();
   	addOneItemInShoppingCart();
   	AlertPopupPage.popup_Btn(popUpCheckOutBtnLabel, driver).click(); 
   	Thread.sleep(1000);
   	
   	Thread.sleep(1000);
   	int changedItemCounter = HomePage.getItemNumber(driver);
   	assertEquals(1, (changedItemCounter - originItemCounter));
   }
   
  // test counter of shopping cart  ---without log in. 
  @Test
  public void checkOutWithEmptyShoppingCartWiutoginInCheckCounter() throws InterruptedException{
  	
  	HomePage.SP_checkOut_Icon(driver).click();
  	
  	Thread.sleep(1000);
  	String emptyMsg = "Oops, there is nothing in your cart.";
  	
  	assertEquals(emptyMsg, CheckOut_Info_Final_Page.getEmptyPageMsg(driver));
  }
   
   //test buy one item and check out without pre-logged in   
   @Test
   public void buyOneItemCheckOutWithOutLogin() throws InterruptedException{
	
	   	clickBuyNow_btn();
	   	addOneItemInShoppingCart();
	   	AlertPopupPage.popup_Btn(popUpCheckOutBtnLabel, driver).click(); 
	   	Thread.sleep(1000);
   	
   	int itemCounter = Integer.parseInt(HomePage.SP_itemCounter_Link(driver).getText());
   
    CheckOut_YouCart_Page.Continue_Btn(driver).click();
    Thread.sleep(1000);
       
    CheckOut_Info_Page.login(driver, userName, pwd);
    
    Thread.sleep(3000);
    CheckOut_Info_Page.clickPurchase(driver);
    CheckOut_YouCart_Page.continueCheckOut(driver);
    Thread.sleep(3000);

    assertEquals("Magic Mouse" , CheckOut_YouCart_Page.getProductName(driver));
    assertEquals(150.00, CheckOut_YouCart_Page.getProductPrice(driver),0.001);
    assertEquals(150.00, CheckOut_YouCart_Page.getProductTotalPrice(driver),0.001);
    assertEquals(1, itemCounter);
    assertEquals("Transaction Results | ONLINE STORE", driver.getTitle());
	
   }
   
   public void clickBuyNow_btn(){
   	HomePage.buyNow_Btn(driver).click();
   }
   
   //add one item in the shoppingcart
   public void addOneItemInShoppingCart(){
   	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   	Slide_Item_Page.wpsc_Buy_Btn(driver).click();
   }

    


}
