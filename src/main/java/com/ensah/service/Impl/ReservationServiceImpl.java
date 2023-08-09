package com.ensah.service.Impl;

import com.ensah.domain.Reservation;
import com.ensah.domain.Terrain;
import com.ensah.domain.User;
import com.ensah.dto.ReservationDto;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        log.info("date of reservation {}", dateofreservation);
        Reservation reservation1 = Reservation.builder().date_reservation(LocalDateTime.now()).date_match(dateofreservation)
                .terrain(terrain).user(user).against_who(againstwho).build();
        Reservation x=reservationRepository.save(reservation1);
        ReservationDto reservationdto = modelMapper.map(x, ReservationDto.class);
        log.info("problem with reservation {} ",reservationdto);
        return new ResponseEntity<>(reservationdto, HttpStatus.OK);
    }
}
