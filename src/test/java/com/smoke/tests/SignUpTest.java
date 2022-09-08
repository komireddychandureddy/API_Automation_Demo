package com.smoke.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.smoke.Web.RegistrationPage;
import com.smoke.Web.WelcomePage;

import controllers.BaseActions;
import listeners.CustomListener;
import utils.ConfigReader;
import utils.ExcelTestDataReader;
@Listeners(CustomListener.class)
public class SignUpTest extends BaseActions
{	

	@Test(dataProvider="getExcelTestData",description ="Verify the Sign up with newuser")
	public void createNewUser(HashMap<String, String> data) 
	{
		
			WelcomePage welcome =new WelcomePage();
			welcome.gotoSignUp();
			RegistrationPage reg =new RegistrationPage();
			reg.registerNewUser(data.get("Name"), getRandomEmail(data.get("Email")), data.get("Password"), data.get("ConfirmPassword"));
			Assert.assertTrue(reg.verifyPuzzle(), "Verify solve puzzle page");
			Assert.assertEquals(false, reg.verifyPuzzle());
	}
	
	@DataProvider
	public Iterator<Object[]> getExcelTestData() 
	{
		String sheetname = this.getClass().getSimpleName();
		ExcelTestDataReader excelReader = new ExcelTestDataReader();
		LinkedList<Object[]> dataBeans = excelReader.getRowDataMap(USERDIR+ConfigReader.getValue("TestData"),sheetname);
		return dataBeans.iterator();
	}
}
