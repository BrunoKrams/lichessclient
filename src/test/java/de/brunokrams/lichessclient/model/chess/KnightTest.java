package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static de.brunokrams.lichessclient.model.chess.Piece.KNIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KnightTest {

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
