package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class PieceMoveTest {

    @Test
    void constructor_throwsException_whenFieldIsUnreachable() {
        fail();
    }

    @Test
    void constructor_throwsException_whenUnneccessaryFileIsProvided() {
        fail();
    }

    @Test
    void constructor_throwsException_whenUnneccessaryFieldIsProvided() {
        fail();
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