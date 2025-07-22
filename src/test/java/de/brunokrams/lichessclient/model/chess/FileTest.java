package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTest {

    @ParameterizedTest
    @CsvSource({"A, a", "G, g"})
    void fromChar_returnsCorrectFile(File expectedFile, char c) {
        // when
        File actualFile = File.fromChar(c);

        // then
        assertThat(Collections.singleton(actualFile)).isEqualTo(Collections.singleton(expectedFile));
    }

    @ParameterizedTest
    @ValueSource(chars = {'1', 'y', 'i', 'A'})
    void fromChar_throwsExpectionWhenIndexIsNotBetweenaAndh(char c) {
        // when/then
        assertThrows(IllegalArgumentException.class, () -> File.fromChar(c));
    }
}