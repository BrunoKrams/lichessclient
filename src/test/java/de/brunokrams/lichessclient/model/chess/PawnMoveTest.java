package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PawnMoveTest {

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {
                    "A,TAKE,C3,null,false",// Target field is not reachable from file
                    "null,MOVE,D8,null,false",// Target field is on eighth rank but no promotion piece is provided
                    "null,MOVE,D7,QUEEN,false",// Target field is not on eight rank but promotion piece is provided
                    "null,MOVE,C3,null,true",// enPassant is true but movetype is move
                    "null,MOVE,C4,null,true",// enPassant is true but targetField is no en passant field
                    "null,TAKE,A5,null,false",// MoveType is Take but File is not provided
                    "E,MOVE,E5,null,false",// MoveType is Move but File is provided
                    "null,MOVE,A8,KING,false"// Promotion to king is dissallowed
            }
    )
    void constructor_throwsException_whenParametersAreInconsistent(File file, MoveType moveType, Field targetField, Piece promotionPiece, boolean enPassant) {
        // when / then
        assertThatThrownBy(() -> new PawnMove(file, moveType, targetField, promotionPiece, enPassant))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(
            nullValues = "null",
            value = {"null,MOVE,E4,null,false,e4",
                    "null,MOVE,E8,QUEEN,false,e8=Q",
                    "E,TAKE,D3,null,true,exd3 e.p.",
                    "E,TAKE,D4,null,false,exd4",
                    "E,TAKE,D8,ROOK,false,exd8=R"})
    void asSan_doesWhatItShould(File file, MoveType moveType, Field targetField, Piece promotionPiece, boolean enPassant, String expectedSan) {
        // given
        Move move = new PawnMove(file, moveType, targetField, promotionPiece, enPassant);

        // when
        String actualSan = move.asSan();

        // then
        assertThat(actualSan).isEqualTo(expectedSan);
    }

}
