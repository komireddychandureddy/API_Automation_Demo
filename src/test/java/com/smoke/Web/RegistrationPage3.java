package com.smoke.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import controllers.BaseActions;
import io.appium.java_client.windows.WindowsDriver;

public class RegistrationPage3 extends BaseActions{

	private WindowsDriver driver;
	public RegistrationPage3 (){
		this.driver=getWinDriver();
	}
	
	@FindBy(id= "ap_customer_name")
	WebElement input_name;
	@FindBy(id= "ap_email")
	WebElement input_email;
	@FindBy(id= "ap_password")
	WebElement input_password;
	@FindBy(id= "ap_password_check")
	WebElement input_confirmPassword;
	
	
	@FindBy(id= "continue")
	WebElement btn_continue;
	
	
	@FindBy(id= "home_children_button")
	WebElement btn_solvePuzzle;
	
	
	public boolean registerNewUser(String name, String email, String password, String confirmPassword) {
		inputText(input_name, name,"Enter name: "+name);
		inputText(input_email, email,"Enter email: "+email);
		inputText(input_password, password,"Enter password: "+password);
		inputText(input_confirmPassword, confirmPassword,"Enter confirm password: "+confirmPassword);
		click(btn_continue, "Click on Continue button");		
		return true;
	}
		
	public boolean verifyPuzzle() {
		
		return isWebElementVisible(btn_solvePuzzle, "Verify solve Puzzle button is displayed");
	}
}
