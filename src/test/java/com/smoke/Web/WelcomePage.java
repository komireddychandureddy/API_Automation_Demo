package com.smoke.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import controllers.BaseActions;
import io.appium.java_client.windows.WindowsDriver;

public class WelcomePage extends BaseActions{

	
	
	private WindowsDriver driver;
	public WelcomePage (){
		this.driver=getWinDriver();
	}

	
	@FindBy(id= "nav-link-accountList")
	WebElement link_account;
	@FindBy(id= "createAccountSubmit")
	WebElement btn_createNewAccount;
	
	
	public boolean gotoSignUp() {
		isWebElementVisible(link_account, "Verify Account and list tab");
		click(link_account, "Click on Account");
		click(btn_createNewAccount, "Click on Create Account button");
		return true;
	}
	
	public boolean welcomePage() {
		return	isWebElementVisible(link_account, "Verify Account and list tab in welcome page");	
	}
}
