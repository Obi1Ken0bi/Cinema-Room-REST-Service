package cinema;

public class ReturnResponse {
    private Seat returnedTicket;

    public ReturnResponse(Seat returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public Seat getReturnedTicket() {
        return returnedTicket;
    }
}
