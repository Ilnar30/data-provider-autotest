package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {


    @ValueSource(strings = {"Junit", "TestNG"})
    @ParameterizedTest(name = "Результаты перехода на страницу содержат {0}")
    void searchOnGitHubTest(String testData) {

        open("https://github.com/");
        $("[placeholder='Search GitHub']").setValue(testData).pressEnter();
        $$("li.repo-list-item").shouldBe(CollectionCondition.sizeGreaterThan(0));

    }

    @CsvSource(value = {
            "Junit, A programmer-oriented testing framework for Java.",
            "TestNg, TestNG testing framework",

    })
    @ParameterizedTest(name = "Результаты перехода на страницу содержат \"{1}\" для запроса \"{0}\"")
    void complexSearchOnGitHubTest(String testData, String expectedResult) {
        open("https://github.com/");
        $("[placeholder='Search GitHub']").setValue(testData).pressEnter();
        $$("li.repo-list-item").first().shouldHave(text(expectedResult));
    }


    static Stream<Arguments> ioStartMenuTest() {
        return Stream.of(
                Arguments.of("EN ", List.of("SERVICES", "Crowdtesting", "COVERAGE", "RESOURCES", "COMPANY")),
                Arguments.of("DE ", List.of("SERVICES", "Crowdtesting", "GERÄTEABDECKUNG", "RESSOURCEN", "COMPANY"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
    void ioStartMenuTest(String lang, List<String> expectedButtons) {
        open("https://test.io/");
        $(".location-selector__button").click();
        $$(".location-selector__list a").find(text(lang)).click();
        $$(".top-navigation__row a")
                .filter(visible)
                .shouldHave(CollectionCondition.texts(expectedButtons));
    }
}
