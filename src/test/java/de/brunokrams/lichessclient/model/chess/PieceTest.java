package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static de.brunokrams.lichessclient.model.chess.Field.*;
import static de.brunokrams.lichessclient.model.chess.Piece.*;
import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {


    @Nested
    class KNIGHT_Test {

        @ParameterizedTest
        @MethodSource("knightMovesProvider")
        void getReachableFields_returnsTheCorrectResult(Field field, List<Field> reachableFields) {
            // when
            List<Field> result = KNIGHT.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrderElementsOf(reachableFields);
        }

        static Stream<Arguments> knightMovesProvider() {
            return Stream.of(
                    Arguments.of(H1, List.of(F2, G3)),
                    Arguments.of(Field.D8, List.of(B7, C6, E6, F7)),
                    Arguments.of(Field.D4, List.of(E2, F3, F5, E6, C6, B5, B3, C2))
            );
        }
    }

    @Nested
    class BISHOP_Test {

        @ParameterizedTest
        @MethodSource("bishopMovesProvider")
        void getReachableFields_returnsTheCorrectResult(Field field, List<Field> reachableFields) {
            // when
            List<Field> result = BISHOP.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrderElementsOf(reachableFields);
        }

        static Stream<Arguments> bishopMovesProvider() {
            return Stream.of(
                    Arguments.of(C3, List.of(A1, B2, D4, E5, F6, G7, H8, A5, B4, D2, E1)),
                    Arguments.of(G2, List.of(F1, H3, A8, B7, C6, D5, E4, F3, H1))
            );
        }
    }

    @Nested
    class ROOK_Test {

        @ParameterizedTest
        @MethodSource("rookMovesProvider")
        void getReachableFields_returnsTheCorrectResult(Field field, List<Field> reachableFields) {
            // when
            List<Field> result = ROOK.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrderElementsOf(reachableFields);
        }

        static Stream<Arguments> rookMovesProvider() {
            return Stream.of(
                    Arguments.of(C3, List.of(C1, C2, C4, C5, C6, C7, C8, A3, B3, D3, E3, F3, G3, H3)),
                    Arguments.of(G2, List.of(G1, G3, G4, G5, G6, G7, G8, A2, B2, C2, D2, E2, F2, H2))
            );
        }
    }

    @Nested
    class QUEEN_Test {

        @ParameterizedTest
        @MethodSource("queenMovesProvider")
        void getReachableFields_returnsTheCorrectResult(Field field, List<Field> reachableFields) {
            // when
            List<Field> result = QUEEN.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrderElementsOf(reachableFields);
        }

        static Stream<Arguments> queenMovesProvider() {
            return Stream.of(
                    Arguments.of(C3, List.of(C1, C2, C4, C5, C6, C7, C8, A3, B3, D3, E3, F3, G3, H3,A1, B2, D4, E5, F6, G7, H8, A5, B4, D2, E1)),
                    Arguments.of(G2, List.of(G1, G3, G4, G5, G6, G7, G8, A2, B2, C2, D2, E2, F2, H2,F1, H3, A8, B7, C6, D5, E4, F3, H1))
            );
        }
    }

    @Nested
    class KING_Test {

        @ParameterizedTest
        @MethodSource("kingMovesProvider")
        void getReachableFields_returnsTheCorrectResult(Field field, List<Field> reachableFields) {
            // when
            List<Field> result = KING.getReachableFields(field);

            // then
            assertThat(result).containsExactlyInAnyOrderElementsOf(reachableFields);
        }

        static Stream<Arguments> kingMovesProvider() {
            return Stream.of(
                    Arguments.of(A1, List.of(A2,B1,B2)),
                    Arguments.of(F4, List.of(E3,E4,E5,F3,F5,G3,G4,G5))
            );
        }
    }

}
