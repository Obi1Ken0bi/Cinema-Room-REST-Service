package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatsManager {
    private final int totalRows;
    private final int totalColumns;
    private final Seat[][] seats;
    private final HashMap<UUID,Seat> map=new HashMap<>();


    public SeatsManager() {
        this(9);
    }

    public SeatsManager(int numberOfRows) {
        seats = new Seat[numberOfRows][numberOfRows];
        totalRows = numberOfRows;
        totalColumns = numberOfRows;
        init();
    }

    public SeatsManager(int numberOfRows, int numberOfColumns) {
        seats = new Seat[numberOfRows][numberOfColumns];
        totalRows = numberOfRows;
        totalColumns = numberOfColumns;
        init();
    }

    public List<Seat> getAvailableSeats() {
        ArrayList<Seat> seatsAtOneArray = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < totalColumns; i++) {
            for (int j = 0; j < totalRows; j++) {
                if (seats[i][j].isAvailable())
                    seatsAtOneArray.add(seats[i][j]);
                k++;
            }
        }
        return seatsAtOneArray;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }


    private void init() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                seats[i][j] = new Seat(i + 1, j + 1);
            }
        }
    }

    private boolean exists(int row, int column) {
        return !(row > totalRows | column > totalColumns|row<=0|column<=0);
    }

    @JsonIgnore
    public void buySeat(int row, int column,UUID uuid) throws TicketException {
        if (exists(row, column)) {
            if (seats[row - 1][column - 1].isAvailable()) {
                seats[row - 1][column - 1].setAvailable(false);
                map.put(uuid,seats[row - 1][column - 1]);

            } else {
                throw new TicketException("The ticket has been already purchased!");
            }
        } else {
            throw new TicketException("The number of a row or a column is out of bounds!");
        }
    }
    @JsonIgnore
    public Seat returnSeat(UUID uuid) throws TicketException {
        Seat seat=map.remove(uuid);
        if (seat==null)
            throw new TicketException("Wrong token!");
        seat.setAvailable(true);
        return seat;
    }
    @JsonIgnore
    public Map<String,Integer> getStats(){
        Integer income=0;
        Integer number=0;
        Map<String,Integer> statsMap=new HashMap<>();
        for (Seat s:map.values()){
           income+= s.getPrice();
           number++;
        }
        statsMap.put("current_income",income);
        statsMap.put("number_of_available_seats",81-number);
        statsMap.put("number_of_purchased_tickets",number);
        return statsMap;
    }


}
