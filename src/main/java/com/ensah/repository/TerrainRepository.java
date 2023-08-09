package com.ensah.repository;

import com.ensah.domain.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerrainRepository extends JpaRepository<Terrain,Long> {

    Terrain findByName(String name);
}
