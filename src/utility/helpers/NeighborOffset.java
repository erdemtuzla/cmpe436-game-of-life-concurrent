package utility.helpers;

public enum NeighborOffset {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);

    private final int deltaRow;
    private final int deltaCol;

    /**
     * Constructor for the neighbor offset.
     * @param deltaRow The change in the row index.
     * @param deltaCol The change in the column index.
     */
    NeighborOffset(int deltaRow, int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }
}