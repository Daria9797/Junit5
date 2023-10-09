import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class ParametraizingTests extends TestBase{

    @CsvSource(value = {
        "English, The free and fun way to learn music",
        "Deutsch, Lerne Musik kostenlos"
    })
    @Tag("BLOCKER")
    @ParameterizedTest(name = "The title {1}  displays in {0} language" )
    void titleMatchesLanguage(String language,String text){

         open("/ru");
         $(".current-lang").click();
         $(byText(language)).click();
         $(".moduletable").shouldHave(Condition.text(text));

    }

    @ValueSource(strings = {
            "Piano",
            "Guitar",
            "Bass Guitar"
    })
    @Tags({@Tag("BLOCKER"),@Tag("REGRESS")})
    @ParameterizedTest(name = "Section Tools has subsection witn name {0}" )
    void  toolHasSubmitNotice(String tool){
        open("/tools");
        $(".article__content").shouldHave(Condition.text(tool));

    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(

                Arguments.of("Guitar", List.of("Mark", "Highlight","Play","Tuner","Sounds")),

                Arguments.of("Bass Guitar", List.of("Mark", "Highlight", "Tuner"))
        );
    }
    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest(name = "Subsection {0} has submits {1}")
    @Tags({@Tag("BLOCKER"),@Tag("SMOKE")})
    void  subsectionHasSubmits(String tool,List<String> submits){
        open("/tools");
        $$(".article__column__box h2").findBy(Condition.text(tool)).click();
        $$(".buttons-module button").shouldHave(CollectionCondition.texts(submits));
    }














}
