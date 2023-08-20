package com.ensah.resource.user;
import com.ensah.domain.Reservation;
import com.ensah.domain.Terrain;
import com.ensah.domain.User;
import com.ensah.dto.*;
import com.ensah.payload.request.ReservationRequest;
import com.ensah.repository.ReservationRepository;
import com.ensah.repository.TerrainRepository;
import com.ensah.repository.UserRepository;
import com.ensah.service.ReservationService;
import com.ensah.utils.UserUtils;
import com.ensah.web.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/")
    public ResponseEntity<ReservationDto> reservate(@RequestBody ReservationRequest reservation){
        return reservationService.reservate(reservation);
    }

    @PostMapping("/reservationofcurrentuser")
    public ResponseEntity<List<LocalDateTime>> findAllDatesofcurrentUser(@RequestBody GetDatesAboutReservation detail) {
        return reservationService.findAllDatesofcurrentUser(detail.getAcademicEmail(),detail.getNameofterrain());
    }

    @PostMapping("/reservationofnocurrentuser")
    public ResponseEntity<List<LocalDateTime>> findAllDatesofNocurrentUser(@RequestBody GetDatesAboutReservation detail) {
        return reservationService.findAllDatesofNocurrentUser(detail.getAcademicEmail(),detail.getNameofterrain());
    }


    @PostMapping("/deletereservation")
    public ResponseEntity<Integer> DeleteReservation (@RequestBody  DeleteReservationDto reservationDto){
            return reservationService.deleteReservation(reservationDto);
    }


    @PostMapping("/reservationLoggedUserfortoday")
    public ResponseEntity<Long> getReservationLoggedUserfortoday(@RequestBody UserReservationDate request ){
        return  reservationService.getReservationLoggedUserfortoday(request);
    }


    @PostMapping("/matchesoftoday")
    public ResponseEntity<List<ReservationDto>> getMatchesofToday(@RequestBody ReservationOfToday today) {
       return reservationService.getMatchesofToday(today);
    }

}

