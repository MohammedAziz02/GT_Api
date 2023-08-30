package com.ensah.service.Impl;

import com.ensah.domain.Reservation;
import com.ensah.domain.Terrain;
import com.ensah.domain.User;
import com.ensah.dto.DeleteReservationDto;
import com.ensah.dto.ReservationDto;
import com.ensah.dto.ReservationOfToday;
import com.ensah.dto.UserReservationDate;
import com.ensah.payload.request.ReservationRequest;
import com.ensah.repository.ReservationRepository;
import com.ensah.repository.TerrainRepository;
import com.ensah.repository.UserRepository;
import com.ensah.service.ReservationService;
import com.ensah.utils.DateConverter;
import com.ensah.utils.UserUtils;
import com.ensah.web.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {



    @Autowired
    private TerrainRepository terrainRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DateConverter dateConverter;

    @Override
    public ResponseEntity<ReservationDto> reservate(ReservationRequest reservation) {
        String loggeduser= UserUtils.getLoggedinUser();
        User user= userRepository.findUserByAcademicemail(loggeduser).orElseThrow(()-> new NotFoundException("user with email " +loggeduser+ " not found"));
        log.info("Reserving reservation {}", reservation);
        String terrainname=reservation.getTerrain();
        Terrain terrain = terrainRepository.findByName(terrainname);
        log.info("Terrain of reservation {}", terrain);
        String againstwho=reservation.getAgainstwho();
        log.info("againt  of who {}", againstwho);
        LocalDateTime dateofreservation = reservation.getDateofreservation();
        LocalDateTime dateGMT= dateConverter.convertToGmtPlusOne(dateofreservation);
        log.info("date of reservation {}", dateGMT);
        Reservation reservation1 = Reservation.builder().date_reservation(LocalDateTime.now()).date_match(dateGMT)
                .terrain(terrain).user(user).against_who(againstwho).build();
        Reservation x=reservationRepository.save(reservation1);
        ReservationDto reservationdto = modelMapper.map(x, ReservationDto.class);
        log.info("problem with reservation {} ",reservationdto);
        return new ResponseEntity<>(reservationdto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LocalDateTime>> findAllDatesofNocurrentUser(String email,String nameterrain) {

        List<LocalDateTime>  listeofDates= reservationRepository.findAllDatesofNocurrentUser(email,nameterrain).orElseThrow(()-> new NotFoundException("no Reservations for user "+ email));
        return new ResponseEntity<>(listeofDates, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LocalDateTime>> findAllDatesofcurrentUser(String email,String nameterrain) {
        List<LocalDateTime>  listeofDates= reservationRepository.findAllDatesofcurrentUser(email,nameterrain).orElseThrow(()-> new NotFoundException("no Reservations for user "+ email));
        return new ResponseEntity<>(listeofDates, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> deleteReservation(DeleteReservationDto deleteReservationDto) {
        // il faut donner le milisecondes 0 pour avoir une correspondance entre la date et la date en bd
        LocalDateTime dateTime= dateConverter.convertToGmtPlusOne(deleteReservationDto.getDateofmatch());
        Integer x= reservationRepository.deleteReservationsByTerrainNameAndDateMatch(deleteReservationDto.getNameofterrain(),dateTime);
        return  new ResponseEntity<>(x,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> getReservationLoggedUserfortoday(UserReservationDate request) {
        return new ResponseEntity<>(reservationRepository.getReservationLoggedUserfortoday(request.getAcademicEmail(),
                request.getTerrain(),dateConverter.convertToGmtPlusOne(request.getStartDate()),dateConverter.convertToGmtPlusOne(request.getEndDate())),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<ReservationDto>> getMatchesofToday( ReservationOfToday today) {
        List<ReservationDto> x = reservationRepository.findByDate_match(dateConverter.convertToGmtPlusOne(today.getToday00()),dateConverter.convertToGmtPlusOne(today.getToday23()),today.getTerrainame());
        if(x.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(x, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReservationDto>> getMyReservation(String academicEmail) {
        List<ReservationDto> reservationlist = reservationRepository.allReservationMadeByUser(academicEmail);
        if(reservationlist.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(reservationlist,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> allReservations = reservationRepository.allReservations();
        if(allReservations.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(allReservations,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> deleteReservationById(Long id) {
        log.info("weeeeeeeeeeeeeeeeeee "+id);
        return new ResponseEntity<>(reservationRepository.deleteReservationById(id),HttpStatus.OK);
    }
}
