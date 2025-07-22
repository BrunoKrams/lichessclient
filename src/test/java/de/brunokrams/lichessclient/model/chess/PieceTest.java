package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PieceTest {

    @ParameterizedTest
    @CsvSource({"KNIGHT, N", "BISHOP, B"})
    void fromChar_returnsCorrectFile(Piece expectedPiece, char c) {
        // when
        Piece acutalPiece = Piece.fromChar(c);

        // then
        assertThat(acutalPiece).isEqualTo(expectedPiece);
    }

    @ParameterizedTest
    @ValueSource(chars = {'1', 'y', 'i', 'A'})
    void fromChar_throwsExpectionWhenCharIsInvalid(char c) {
        // when/then
        assertThrows(IllegalArgumentException.class, () -> Piece.fromChar(c));
    }

}
