package cinema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    @Autowired
    private SeatsManager seatsManager;

    @GetMapping("/seats")
    public SeatsManager getSeats() {
        return seatsManager;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchase(@RequestBody SeatToBuy seatToBuy) {
        Seat seat = new Seat(seatToBuy.getRow(), seatToBuy.getColumn());
        try {
            UUID uuid=UUID.randomUUID();
            seatsManager.buySeat(seatToBuy.getRow(), seatToBuy.getColumn(),uuid);
            return new ResponseEntity<Object>(new PurchaseResponse(seat,uuid), HttpStatus.OK);
        } catch (TicketException e) {
            Map<String,String> map=new HashMap<>();
            map.put("error",e.getMessage());
            return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody HashMap<String,String> body){
        UUID uuid= UUID.fromString(body.get("token"));
        try {
           Seat seat= seatsManager.returnSeat(uuid);
           return new ResponseEntity<>(new ReturnResponse(seat),HttpStatus.OK);
        } catch (TicketException e) {
            Map<String,String> map=new HashMap<>();
            map.put("error",e.getMessage());
            return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/stats")
    public ResponseEntity<Object> stats(@RequestBody(required = false) String password){
        if(password==null||password.equals("super_secret")){
            Map<String,String> map=new HashMap<>();
            map.put("error","The password is wrong!");
            return new ResponseEntity<Object>(map,HttpStatus.UNAUTHORIZED);
        }
        else{
           return new ResponseEntity<Object>( seatsManager.getStats(),HttpStatus.OK);
        }
    }

}

