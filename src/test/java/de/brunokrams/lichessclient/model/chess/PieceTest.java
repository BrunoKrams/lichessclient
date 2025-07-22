package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static de.brunokrams.lichessclient.model.chess.Piece.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PieceTest {

    @Nested
    class KNIGHT_Test {
        @ParameterizedTest
        @CsvSource({
                "H1,F2,G3",
                "D8,B7,C6,E6,F7",
                "D4,E2,F3,F5,E6,C6,B5,B3,C2"
        })
        void getReachableFields_returnsTheCorrectResult(Field field, Field... reachableFields) {
            // when
            List<Field> result = KNIGHT.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrder(reachableFields);
        }
    }

    @Nested
    class BISHOP_Test {
        @ParameterizedTest
        @CsvSource
        void getReachableFields_returnsTheCorrectResult(Field field, Field... reachableFields) {
            // when
            List<Field> result = BISHOP.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrder(reachableFields);
        }
    }
    @Nested
    class ROOK_Test {
        @ParameterizedTest
        @CsvSource
        void getReachableFields_returnsTheCorrectResult(Field field, Field... reachableFields) {
            // when
            List<Field> result = ROOK.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrder(reachableFields);
        }
    }
    @Nested
    class QUEEN_Test {
        @ParameterizedTest
        @CsvSource
        void getReachableFields_returnsTheCorrectResult(Field field, Field... reachableFields) {
            // when
            List<Field> result = QUEEN.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrder(reachableFields);
        }
    }
    @Nested
    class KING_Test {
        @ParameterizedTest
        @CsvSource
        void getReachableFields_returnsTheCorrectResult(Field field, Field... reachableFields) {
            // when
            List<Field> result = KING.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrder(reachableFields);
        }
    }

}
