package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;

class PieceMoveTest {

    @Test
        // BISHOP       @CsvSource({"A1,H7", "D6,E4", "E1, F3"})
        //              @CsvSource({"A, H5", "B,G5", "G,A5"})
        // KNIGHT       @CsvSource({"A1,E4","D6,A1", "E2, F5"})
        //              @CsvSource({"A,E4","D,A1"})
        // ROOK         @CsvSource({"A1,E4","D6,A1", "E2, F5"})
    void constructor_throwsException_whenFieldIsUnreachable() {
        fail();
    }

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {
                    "KING,E,null,MOVE,E5",
                    "QUEEN,D,null,MOVE,D3"
            }
    )
    void constructor_throwsException_whenUnneccessaryFileIsProvided(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField) {
        // when / then
        assertThatThrownBy(() -> new PieceMove(piece, originFile, originField, moveType, targetField)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {
                    "KING,null,E4,MOVE,E5",
                    "QUEEN,null,C3,MOVE,D3"
            }
    )
    void constructor_throwsException_whenUnneccessaryFieldIsProvided(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField) {
        // when / then
        assertThatThrownBy(() -> new PieceMove(piece, originFile, originField, moveType, targetField)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {
                    "KNIGHT,E,E4,MOVE,D6",
                    "BISHOP,C,C1,MOVE,F4",
                    "ROOK,A,A3,TAKE,H3"
            })
    void constructor_throwsException_whenBothFileAndFieldAreProvided(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField) {
        // when / then
        assertThatThrownBy(() -> new PieceMove(piece, originFile, originField, moveType, targetField)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {
                    "BISHOP,null,D3,MOVE,E4,Bd3e4",
                    "ROOK,null,null,MOVE,A4,Ra4",
                    "QUEEN,null,null,TAKE,C3,Qxc3",
                    "KNIGHT,E,null,MOVE,C3,Nec3",
                    "KING,null,null,TAKE,A8,Kxa8"}
    )
    void asSan_doesWhatItShould(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField, String expectedSan) {
        // given
        Move move = new PieceMove(piece, originFile, originField, moveType, targetField);

        // when
        String actualSan = move.asSan();

        // then
        assertThat(actualSan).isEqualTo(expectedSan);
    }
}