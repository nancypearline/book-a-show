package com.nancy.booking.show.dao;

import com.nancy.booking.show.entity.Show;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowDAO extends BaseDAO<Show> {

    Optional<Show> findByShowNumber(int showNo);

    boolean existsByShowNumber(int showNo);

}
