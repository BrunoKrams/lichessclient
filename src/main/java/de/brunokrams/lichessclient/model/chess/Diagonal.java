package de.brunokrams.lichessclient.model.chess;

import java.util.ArrayList;
import java.util.List;

import static de.brunokrams.lichessclient.model.chess.Field.*;

public enum Diagonal {

    // ↗ Diagonals (bottom-left to top-right)
    A1_H8(A1, B2, C3, D4, E5, F6, G7, H8),
    A2_G8(A2, B3, C4, D5, E6, F7, G8),
    A3_F8(A3, B4, C5, D6, E7, F8),
    A4_E8(A4, B5, C6, D7, E8),
    A5_D8(A5, B6, C7, D8),
    A6_C8(A6, B7, C8),
    A7_B8(A7, B8),
    A8_A8(A8),
    B1_H7(B1, C2, D3, E4, F5, G6, H7),
    C1_H6(C1, D2, E3, F4, G5, H6),
    D1_H5(D1, E2, F3, G4, H5),
    E1_H4(E1, F2, G3, H4),
    F1_H3(F1, G2, H3),
    G1_H2(G1, H2),
    H1_H1(H1),

    // ↘ Diagonals (top-left to bottom-right)
    A8_H1(A8, B7, C6, D5, E4, F3, G2, H1),
    A7_G1(A7, B6, C5, D4, E3, F2, G1),
    A6_F1(A6, B5, C4, D3, E2, F1),
    A5_E1(A5, B4, C3, D2, E1),
    A4_D1(A4, B3, C2, D1),
    A3_C1(A3, B2, C1),
    A2_B1(A2, B1),
    A1_A1(A1),
    B8_H2(B8, C7, D6, E5, F4, G3, H2),
    C8_H3(C8, D7, E6, F5, G4, H3),
    D8_H4(D8, E7, F6, G5, H4),
    E8_H5(E8, F7, G6, H5),
    F8_H6(F8, G7, H6),
    G8_H7(G8, H7),
    H8_H8(H8);

    private final List<Field> fields;

    Diagonal(Field... fields) {
        this.fields = List.of(fields);
    }

    public List<Field> getFields() {
        return fields;
    }

    public static List<Diagonal> getContainingDiagonals(Field field) {
        List<Diagonal> result = new ArrayList<>();
        for (Diagonal diagonal : values()) {
            if (diagonal.fields.contains(field)) {
                result.add(diagonal);
            }
        }
        return result;
    }
}
