package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;

public class SearchPopup {

    SelenideElement searchPopup = $("div.search-popup");

    SelenideElement searchInput = $("input#search-in-popup");

    SelenideElement liveCheckbox = $x("//div[@class='search-popup__settings']//input[@id='checkbox_1']/..");

    SelenideElement sportsCheckbox = $x("//div[@class='search-popup__settings']//input[@id='checkbox_2']/..");

    SelenideElement noResultsBlock = $("div.search-popup__nothing-find");

    SelenideElement clearButton = $("div.search-popup__clear");

    SelenideElement searchButton = $("button.search-popup__button");

    SelenideElement closePopupButton = $("div.search-popup__close");

    ElementsCollection searchResults = $$("div.search-popup-events__item");

    ElementsCollection liveLabels = $$("span.search-popup-event__live");


    @Step("Wait until search results will displayed")
    public SearchPopup waitUntilSearchResultsWillBeDisplayed() {
        $("div.search-popup-events").shouldBe(Condition.visible);
        return this;
    }

    public int getCounterValue() {
        return Integer.parseInt($("span.search-popup__text--accent").getText());
    }

    @Step("Verify that test results contains query {query} ")
    public SearchPopup verifyTestResults(String query) {
        waitUntilSearchResultsWillBeDisplayed();
        searchInput.shouldHave(Condition.attribute("value", query));
        Assert.assertEquals(searchResults.size(), getCounterValue());
        for (SelenideElement result : searchResults) {
            result.shouldHave(Condition.text(query));
        }
        return this;
    }

    @Step("Verify that test results contains query {query} or {secondQuery}")
    public SearchPopup verifyTestResults(String query, String secondQuery) {
        waitUntilSearchResultsWillBeDisplayed();
        Assert.assertEquals(searchResults.size(), getCounterValue());
        for (SelenideElement result : searchResults) {
                result.shouldHave(Condition.or("Checking for the presence of text in the result", Condition.matchText(query),Condition.matchText(secondQuery)));
        }
        return this;
    }

    @Step("Verify that 'No results' message is displayed")
    public SearchPopup verifyDisplayedNoResults() {
        noResultsBlock.shouldBe(Condition.visible);
        noResultsBlock.shouldHave(Condition.text("No results"));
        Assert.assertEquals(getCounterValue(), 0);
        return this;
    }

    @Step("Click on Sports checkbox")
    public SearchPopup clickOnSportsCheckbox() {
        sportsCheckbox.click();
        return this;
    }

    @Step("Click on Live checkbox")
    public SearchPopup clickOnLiveCheckbox() {
        liveCheckbox.click();
        return this;
    }

    @Step("Click on Clear button and verify that input is clear")
    public SearchPopup clickOnClearButtonAndVerifyThatInputClear() {
        clearButton.click();
        searchInput.shouldHave(Condition.attribute("value", ""));
        return this;
    }

    @Step("Set value = {query} in search input of popup and click Search")
    public SearchPopup setValueInSearchInputOfPopupAndClickSearch(String query) {
        searchInput.setValue(query);
        searchButton.click();
        return this;
    }

    @Step("Verify all results has Live labels")
    public void verifyAllResultsHasLiveLabels() {
        Assert.assertEquals(liveLabels.size(), getCounterValue());
    }

    @Step("Verify all results has not Live labels")
    public void verifyAllResultsHasNotLiveLabels() {
        Assert.assertEquals(liveLabels.size(), 0);
    }

    @Step("Click Close popup button and verify that popup is closed")
    public void clickClosePopupButtonAndVerifyThatPopupClose() {
        closePopupButton.click();
        searchPopup.shouldBe(Condition.disappear);
    }
}
