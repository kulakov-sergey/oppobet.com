package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.*;

public class HomePage {
    private SelenideElement searchInput = $("input.searchInput");
    private SelenideElement searchButton = $("button#b-searchBut-live");
    public ElementsCollection teamsNamesFromLiveBetsTable = $$("div.c-events__team");
    public ElementsCollection teamsNamesFromSportsBetsTable = $$x("//section[@class='c-section line']//span[@class='c-events__teams']");

    @Step("Set value {searchQuery} in search input and click search")
    public SearchPopup fillSearchInputAndClickSearch(String searchQuery) {
        searchInput.setValue(searchQuery);
        searchButton.click();
        return new SearchPopup();
    }

    @Step("Set value {searchQuery} and {secondSearchQuery} in search input and click search")
    public SearchPopup fillSearchInputAndClickSearch(String searchQuery, String secondSearchQuery) {
        searchInput.setValue(searchQuery + " " + secondSearchQuery);
        searchButton.click();
        return new SearchPopup();
    }

    @Step("Set value {searchQuery} in search input and press Enter")
    public SearchPopup fillSearchInputAndPressEnter(String searchQuery) {
        searchInput.setValue(searchQuery)
                .pressEnter();
        return new SearchPopup();
    }

    @Step("Get team name from LiveBets table (first world)")
    public String getTeamNameFromLiveBetsTable(int index) {
        return teamsNamesFromLiveBetsTable.get(index).getText().split(" ")[0];
    }

    @Step("Get team names from Sports table (first world)")
    public String getTeamNamesFromSportsBetsTable(int index) {
        return teamsNamesFromSportsBetsTable.get(index).getAttribute("title").split(" ")[0];
    }
}