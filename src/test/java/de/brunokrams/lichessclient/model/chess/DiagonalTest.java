package de.brunokrams.lichessclient.model.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DiagonalTest {

    @Test
    @CsvSource({"F5, C8_H3, B1_H7", "G8, G8_H7, A2,G8"})
    void getContainingDiagonals_returnsTheCorrectResult(Field field, Diagonal first, Diagonal second) {
        // when
        List<Diagonal> result = Diagonal.getContainingDiagonals(field);

        // then
        assertThat(result).containsExactly(first, second);
    }
}
