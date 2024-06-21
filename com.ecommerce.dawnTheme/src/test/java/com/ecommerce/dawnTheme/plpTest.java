package com.ecommerce.dawnTheme;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import dawnObjectRepository.homePage;
import dawnObjectRepository.pdpPage;
import dawnObjectRepository.plpPage;
import genericUtility.BaseClass;
import genericUtility.propertyFileUtllity;

public class plpTest extends BaseClass {

	// 1. Verify the product count
	@Test(priority = 1)
	public void collectionHeading() throws Exception {
		driver.get(
				"https://themes.shopify.com/themes/dawn/styles/default/preview?surface_detail=free-themes&surface_inter_position=1&surface_intra_position=6&surface_type=collection");
		homePage h = new homePage(driver);
		driver.switchTo().frame(h.getIframe());
		h.getSearchIcon().click();
		h.getSearchTextfield().sendKeys("Bags");
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		plpPage p = new plpPage(driver);
		String productCount = p.getProductName().getText();
		Reporter.log(productCount, true);
		boolean count = p.getProductCount().isDisplayed();
		Assert.assertTrue(count);

	}

	// 2: Verify clicking on product card/title redirects to respective pdp
	@Test(priority = 2, dependsOnMethods = "collectionHeading")
	public void selectProduct() {
		String proName = "Business Bag";
		List<WebElement> productCard = driver
				.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']"));
		for (WebElement pro : productCard) {
			if (pro.getText().contains(proName)) {
				pro.click();
				break;
			}
		}
		pdpPage pdp = new pdpPage(driver);
		String productTitle = pdp.getProductTitle().getText();
		Assert.assertEquals(proName, productTitle, "Improper product is displayed");
	}

	// 3: Verify the availablity filter functionality
	@Test(priority = 3)
	public void availabilityDropdownFunctionality() throws Exception {
		driver.get(
				"https://themes.shopify.com/themes/dawn/styles/default/preview?surface_detail=free-themes&surface_inter_position=1&surface_intra_position=6&surface_type=collection");
		homePage h = new homePage(driver);
		driver.switchTo().frame(h.getIframe());
		h.getSearchIcon().click();
		h.getSearchTextfield().sendKeys("Bags");
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		plpPage p = new plpPage(driver);
		p.getAvailabilityDropdown().click();
		WebElement instock = p.getInStockCheckbox();
		Actions act = new Actions(driver);
		act.moveToElement(instock).click(instock).build().perform();
		Thread.sleep(5000);
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.keyRelease(KeyEvent.VK_ESCAPE);
		String proName = "Business Bag";
		Thread.sleep(5000);
		List<WebElement> productCard = driver
				.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']"));
		for (WebElement pro : productCard) {
			if (pro.getText().contains(proName)) {
				pro.click();
				break;
			}
		}
		pdpPage pdp = new pdpPage(driver);
		String productTitle = pdp.getProductTitle().getText();
		Assert.assertEquals(proName, productTitle, "Improper product is displayed");
	}

	// 4: Verify the color filter functionality
	@Test(priority = 4)
	public void colorDropdownFunctionality() throws Exception {
		// propertyFileUtllity pUtil = new propertyFileUtllity();
		String color = "Green";
		String proName = "Mini Sera Tote";
		driver.get(
				"https://themes.shopify.com/themes/dawn/styles/default/preview?surface_detail=free-themes&surface_inter_position=1&surface_intra_position=6&surface_type=collection");
		homePage h = new homePage(driver);
		driver.switchTo().frame(h.getIframe());
		h.getBagsDropdown().click();
		String text = "Tote bags";
		driver.findElement(By.xpath("//ul[@id='HeaderMenu-MenuList-1']//a[contains(text(),'" + text + "')]")).click();
		plpPage p = new plpPage(driver);
		p.getColorDropdown().click();
		List<WebElement> a = driver.findElements(By.xpath("//li[@class='list-menu__item facets__item']"));
		Thread.sleep(2000);
		for (WebElement b : a) {
			if (b.getText().contains(color)) {
				b.click();
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_ESCAPE);
				r.keyRelease(KeyEvent.VK_ESCAPE);
				break;
			}

		}
		Thread.sleep(2000);
		List<WebElement> productCard = driver
				.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']"));
		for (WebElement pro : productCard) {
			if (pro.getText().contains(proName)) {
				pro.click();
				break;
			}
		}
		pdpPage pdp = new pdpPage(driver);
		String productTitle = pdp.getProductTitle().getText();
		Assert.assertEquals(proName, productTitle, "Improper product is displayed");
	}

	// 5: Verify the sort by functionality
	@Test(priority = 5)
	public void sortbyFunctionality() throws Exception {
		driver.get(
				"https://themes.shopify.com/themes/dawn/styles/default/preview?surface_detail=free-themes&surface_inter_position=1&surface_intra_position=6&surface_type=collection");
		homePage h = new homePage(driver);
		driver.switchTo().frame(h.getIframe());
		h.getShoesDropdown().click();
		String text = "Shop all";
		driver.findElement(By.xpath("//ul[@id='HeaderMenu-MenuList-2']//a[contains(text(),'" + text + "')]")).click();
		plpPage p = new plpPage(driver);
		Select s = new Select(p.getSortbyBdropdown());
		String sortOption = "price-descending";
		s.selectByValue(sortOption);
		Thread.sleep(2000);
		// Enter product name
		String proName = "Louise Slide Sandal";
		List<WebElement> productCard = driver
				.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']"));
		for (WebElement pro : productCard) {
			if (pro.getText().contains(proName)) {
				pro.click();
				break;
			}
		}
		
		pdpPage pdp = new pdpPage(driver);
		String productTitle = pdp.getProductTitle().getText();
		Assert.assertEquals(proName, productTitle, "Improper product is displayed");
	}

	// 6: Verify the procefilter functionality
	@Test(priority = 6)
	public void priceFIlterFunctionality() throws Exception {
		driver.get(
				"https://themes.shopify.com/themes/dawn/styles/default/preview?surface_detail=free-themes&surface_inter_position=1&surface_intra_position=6&surface_type=collection");
		homePage h = new homePage(driver);
		driver.switchTo().frame(h.getIframe());
		h.getShoesDropdown().click();
		String text = "Shop all";
		driver.findElement(By.xpath("//ul[@id='HeaderMenu-MenuList-2']//a[contains(text(),'" + text + "')]")).click();
		plpPage p = new plpPage(driver);
		Thread.sleep(3000);
		p.getPriceDropdown().click();
		WebElement priceFrom = p.getPriceFromValue();
		WebElement priceTo = p.getPriceToValue();
		Thread.sleep(2000);
		Robot r = new Robot();
		for (int i = 0; i < 10; i++) {
			r.keyPress(KeyEvent.VK_DOWN);
			r.keyRelease(KeyEvent.VK_DOWN);
		}
		Actions act = new Actions(driver);
		act.moveToElement(priceFrom).click(priceFrom).build().perform();
		p.getPriceFromEnterValue().sendKeys("100");
		act.moveToElement(priceTo).click(priceTo).build().perform();
		p.getPriceToEnterValue().sendKeys("500");
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.keyRelease(KeyEvent.VK_ESCAPE);
		Thread.sleep(2000);
		// Enter product name
		String proName = "Louise Slide Sandal";
		List<WebElement> productCard = driver
				.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']"));
		for (WebElement pro : productCard) {
			if (pro.getText().contains(proName)) {
				pro.click();
				break;
			}
		}
		pdpPage pdp = new pdpPage(driver);
		String productTitle = pdp.getProductTitle().getText();
		Assert.assertEquals(proName, productTitle, "Improper product is displayed");

	}

}
