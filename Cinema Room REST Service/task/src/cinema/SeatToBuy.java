package cinema;

public class SeatToBuy {
    private  int row;
    private  int column;

    public SeatToBuy(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public SeatToBuy() {
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
