package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RankTest {

    @ParameterizedTest
    @CsvSource({"EIGHT, 8", "FIVE, 5"})
    void fromIndex_returnsCorrectRank(Rank expectedRank, int index) {
        // when
        Rank acutalRank = Rank.fromIndex(index);

        // then
        assertThat(acutalRank).isEqualTo(expectedRank);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -4, 9, 100})
    void fromIndex_throwsExpectionWhenIndexIsNotBetween1And8(int index) {
        // when/then
        assertThrows(IllegalArgumentException.class, () -> Rank.fromIndex(index));
    }
}