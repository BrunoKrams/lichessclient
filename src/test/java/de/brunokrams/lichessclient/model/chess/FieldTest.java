package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldTest {

    @ParameterizedTest
    @CsvSource({"E,FOUR,e4", "C,THREE,c3", "A,EIGHT,a8"})
    void asSan_doesWhatItShould(File file, Rank rank, String expectedSan) {
        // given
        Field field = Field.fromFileAndRank(file, rank);

        // when
        String actualSan = field.asSan();

        // then
        assertThat(actualSan).isEqualTo(expectedSan);
    }

    @ParameterizedTest
    @CsvSource({"B, FOUR, B4", "H, TWO, H2"})
    void fromFileAndRank_doesWhatItShould(File file, Rank rank, Field expectedField) {
        // when
        Field actualField = Field.fromFileAndRank(file, rank);

        // then
        assertThat(actualField).isEqualTo(expectedField);
    }

}
