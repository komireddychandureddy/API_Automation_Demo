package com.smoke.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.smoke.Web.SearchPage;
import com.smoke.Web.WelcomePage;

import controllers.BaseActions;
import listeners.CustomListener;
import utils.ConfigReader;
import utils.ExcelTestDataReader;
@Listeners(CustomListener.class)
public class SearchItemTest extends BaseActions
{	

	@Test(dataProvider="getExcelTestData",description ="Verify searching an item")
	public void searchItem(HashMap<String, String> data)
	{
		WelcomePage welcome =new WelcomePage();
		welcome.welcomePage();
		SearchPage search =new SearchPage();
		search.searchItem(data.get("Category"),data.get("Item"));
		search.addToCart();
		
	//	Assert.assertTrue(, "Home page is not visiable");
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
