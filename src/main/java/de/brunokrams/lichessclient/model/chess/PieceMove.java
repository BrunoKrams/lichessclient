package de.brunokrams.lichessclient.model.chess;

class PieceMove implements Move {

    private final Piece piece;
    private final File originFile;
    private final Field originField;
    private final MoveType moveType;
    private final Field targetField;

    PieceMove(Piece piece, File originFile, Field originField, MoveType moveType, Field targetField) {
        this.piece = piece;
        this.originFile = originFile;
        this.originField = originField;
        this.moveType = moveType;
        this.targetField = targetField;
    }

    @Override
    public String asSan() {
        return piece.asSan()
                + (originField != null ? originField.asSan() : "")
                + (originFile != null ? originFile.asSan() : "")
                + moveType.asSan()
                + targetField.asSan();
    }
}
