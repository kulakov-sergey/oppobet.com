package tests;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SearchPopup;

public class SearchTests extends BaseTest {
    protected static HomePage homePage = new HomePage();

    @Test(description = "Search by team name (by the first word of the name) from the Live Bets table")
    public void searchLiveEventByTeamName() {
        String searchQuery = homePage.getTeamNameFromLiveBetsTable(0);
        searchAndVerifyResult(searchQuery);
    }

    @Test(description = "Checking the search operation by pressing the Enter key")
    public void checkingTheSearchOperationByPressingTheEnterKey() {
        String searchQuery = homePage.getTeamNameFromLiveBetsTable(0);
        homePage.fillSearchInputAndPressEnter(searchQuery)
                .verifyTestResults(searchQuery);
    }

    @Test(description = "Checking search by value in upper and lower case")
    public void checkingSearchByValueInUpperAndLowerCase() {
        String searchQueryInLowerCase = homePage.getTeamNameFromLiveBetsTable(0).toLowerCase();
        String searchQueryInUpperCase = homePage.getTeamNameFromLiveBetsTable(0).toUpperCase();
        searchAndVerifyResult(searchQueryInLowerCase)
                .clickClosePopupButtonAndVerifyThatPopupClose();
        searchAndVerifyResult(searchQueryInUpperCase);
    }

    @Test(description = "Search Live event by part of team name")
    public void searchLiveEventByPartOfTeamName() {
        String searchQuery = homePage.getTeamNameFromLiveBetsTable(0).substring(0,2);
        searchAndVerifyResult(searchQuery);
    }

    //todo Похоже тест нашел баг. Если ввести в поиск Barys Metallurg, если результат поиска без данных названий Temirtau - Beibarys Atyrau
    @Test(description = "Search by two team names (two words) from the Live Bets table")
    public void searchLiveEventByTwoTeamNames() {
        String searchQuery = homePage.getTeamNameFromLiveBetsTable(0);
        String secondSearchQuery = homePage.getTeamNameFromLiveBetsTable(1);
        homePage.fillSearchInputAndClickSearch(searchQuery, secondSearchQuery)
                .verifyTestResults(searchQuery, secondSearchQuery);
    }

    @Test(description = "Checking the display of Live search results when only the Live checkbox is checked")
    public void checkingTheDisplayOfSearchResultsWhenOnlyTheLiveCheckboxIsChecked() {
        String searchQuery = homePage.getTeamNameFromLiveBetsTable(0);
        searchAndVerifyResult(searchQuery)
                .clickOnSportsCheckbox()
                .verifyTestResults(searchQuery)
                .verifyAllResultsHasLiveLabels();
    }

    @Test(description = "Checking the display of Sports search results when only the Sports checkbox is checked")
    public void checkingTheDisplayOfSearchResultsWhenOnlyTheSportsCheckboxIsChecked() {
        String searchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        searchAndVerifyResult(searchQuery)
                .clickOnLiveCheckbox()
                .verifyTestResults(searchQuery)
                .verifyAllResultsHasNotLiveLabels();
    }

    @Test(dataProvider = "queriesForPositiveTests", description = "Other positive tests (using parametrization)")
    public void positiveSearchWithResults(String searchQuery){
        searchAndVerifyResult(searchQuery);
    }

    @Test(description = "Check missing results when both checkboxes are not checked")
    public void checkingTheDisplayOfNoResultsWhenBothCheckboxesAreNotSelected() {
        String searchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        searchAndVerifyResult(searchQuery)
                .clickOnLiveCheckbox()
                .clickOnSportsCheckbox()
                .verifyDisplayedNoResults();
    }

    @Test(description = "Check search by empty value")
    public void checkForSearchByEmptyValue() {
        searchAndVerifyNoResult("");
    }

    @Test(description = "Check for a search on a non-existent value")
    public void checkForSearchOnNonExistentValue() {
        String searchQuery = RandomStringUtils.randomAlphanumeric(20);
        searchAndVerifyNoResult(searchQuery);
    }

    @Test(dataProvider = "queriesForNegativeTests", description = "Other negative tests (using parametrization)")
    public void negativeSearchWithNoResults(String searchQuery){
        searchAndVerifyNoResult(searchQuery);
    }

    @Test(description = "Checking the clean button")
    public void checkTheClearButton() {
        String searchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        searchAndVerifyResult(searchQuery)
                .clickOnClearButtonAndVerifyThatInputClear();
    }

    @Test(description = "Checking the cross of closing the popup")
    public void checkTheClosePopup() {
        String searchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        homePage.fillSearchInputAndClickSearch(searchQuery)
                .clickClosePopupButtonAndVerifyThatPopupClose();
    }

    @Test(description = "Checking the search in the popup")
    public void checkTheSearchInPopup() {
        String searchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        String secondSearchQuery = homePage.getTeamNamesFromSportsBetsTable(0);
        searchAndVerifyResult(searchQuery)
                .setValueInSearchInputOfPopupAndClickSearch(searchQuery)
                .verifyTestResults(secondSearchQuery);
    }

    private SearchPopup searchAndVerifyResult(String searchQuery) {
        return homePage.fillSearchInputAndClickSearch(searchQuery)
                .verifyTestResults(searchQuery);
    }

    private SearchPopup searchAndVerifyNoResult(String searchQuery) {
        return homePage.fillSearchInputAndClickSearch(searchQuery)
                .verifyDisplayedNoResults();
    }

    @DataProvider(name="queriesForNegativeTests")
    public Object[][] getDataForNegativeTests(){
        return new Object[][]{
                {" \"[|]'~<!--@/*$%^&#*/()?>,.*/\\"},
                {"♣ ☺ ♂"},
                {"^M, \n, \r"},
                {"\" ' `| /\\ , ; :& <>^*?Tab «»"},
                {"<script>alert(\"xss!\")</script>"},
                {"<script>document.getElementByID(\"...\").disabled=true</script>"},
                {"<input onclick=\"javascript:alert('xss');\">"},
                {"<b onmouseover=\"alert('xss!')\">Hello</b>"},
                {"</body>"},
                {"<textarea />"},
                {"<input></input>"},
                {"<form action=\"http://live.hh.ru\"><input type=\"submit\"></form>"},
                {"йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³йцу кен гшщ зхъ"},
                {"   "}
        };
    }

    @DataProvider(name="queriesForPositiveTests")
    public Object[][] getDataForPositiveTests(){
        return new Object[][]{
                {" "},
                {" 1 "}
        };
    }
}
