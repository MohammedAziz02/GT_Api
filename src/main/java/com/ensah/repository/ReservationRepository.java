package com.ensah.repository;
import com.ensah.domain.Reservation;
import com.ensah.dto.ReservationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r.date_match FROM Reservation r WHERE r.user.academicemail <> :email and r.terrain.name=:name and r.date_match > CURRENT_DATE()")
   Optional<List<LocalDateTime>> findAllDatesofNocurrentUser(String email, String name);

    @Query("SELECT r.date_match FROM Reservation r WHERE r.user.academicemail = :email and r.terrain.name=:name and r.date_match > CURRENT_DATE()")
    Optional<List<LocalDateTime>> findAllDatesofcurrentUser(String email, String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE r FROM reservations r INNER JOIN terrains t ON r.terrain_id = t.id WHERE t.name= :terrainname and r.date_match=:datematch ",nativeQuery = true)
    Integer deleteReservationsByTerrainNameAndDateMatch(
            @Param("terrainname") String terrainname,
            @Param("datematch") LocalDateTime datematch
    );


    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.terrain.name = :terrain " +
            "AND r.user.academicemail = :academicEmail " +
            "AND r.date_match BETWEEN :startDate AND :endDate")
    Long getReservationLoggedUserfortoday(String academicEmail, String terrain, LocalDateTime startDate, LocalDateTime endDate);


    @Query("select new com.ensah.dto.ReservationDto(r.id,r.date_reservation,r.date_match,r.against_who,r.terrain,r.user) from Reservation  r where r.date_match >:datetoday00 AND r.date_match <:datetoday23  AND r.terrain.name=:nameterrain")
    List<ReservationDto> findByDate_match(LocalDateTime datetoday00,LocalDateTime datetoday23,String nameterrain);




}
