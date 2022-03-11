package cinema;

import java.util.UUID;

public class PurchaseResponse {
    private UUID token;
    private Seat ticket;


    public PurchaseResponse(Seat seat, UUID uuid) {
        this.ticket = seat;
        this.token = uuid;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
