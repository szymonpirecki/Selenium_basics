package basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import testBase.TestBase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TableTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/table.php");
    }

    @RepeatedTest(10)
    public void shouldListSwissPeaksAbove4000MetersTest() {
        List<String> expectedResult = List.of("2 Dufourspitze Alps", "3 Dom Alps", "4 Weisshorn Alps", "5 Matterhorn Alps", "6 Finsteaarhorn Alps", "7 Jungfrau Alps");
        List<String> filteredMountains = utils.getPeaksDataBasedOnCountryAndHeight("Switzerland", 4000);
        filteredMountains.forEach(System.out::println);
        assertThat(filteredMountains).containsExactlyInAnyOrderElementsOf(expectedResult)
                .size().isEqualTo(expectedResult.size());
    }


}
