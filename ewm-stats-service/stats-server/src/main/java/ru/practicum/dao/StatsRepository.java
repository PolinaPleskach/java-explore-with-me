package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ResponseDto;
import ru.practicum.entity.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stat, Long> {

    @Query("""
            SELECT new ru.practicum.ResponseDto(s.ip, s.uri, COUNT(DISTINCT s.ip))
            FROM Stat AS s
            WHERE s.timestamp BETWEEN :start AND :end AND s.uri IN :uris
            GROUP BY s.ip, s.uri
            ORDER BY COUNT(DISTINCT s.ip) DESC
            """)
    List<ResponseDto> findAllWithUniqueIpWithUris(List<String> uris,
                                                  LocalDateTime start,
                                                  LocalDateTime end);

    @Query("""
            SELECT new ru.practicum.ResponseDto(s.ip, s.uri, COUNT(DISTINCT s.ip))
            FROM Stat AS s
            WHERE s.timestamp BETWEEN :start AND :end
            GROUP BY s.ip, s.uri
            ORDER BY COUNT(DISTINCT s.ip) DESC
            """)
    List<ResponseDto> findAllWithUniqueIpWithoutUris(LocalDateTime start,
                                                     LocalDateTime end);

    @Query("""
            SELECT new ru.practicum.ResponseDto(s.ip, s.uri, COUNT(s.ip))
            FROM Stat AS s
            WHERE s.timestamp BETWEEN :start AND :end AND s.uri IN :uris
            GROUP BY s.ip, s.uri
            ORDER BY COUNT (s.ip) DESC
            """)
    List<ResponseDto> findAllWithUris(List<String> uris,
                                      LocalDateTime start,
                                      LocalDateTime end);

    @Query("""
            SELECT new ru.practicum.ResponseDto(s.ip, s.uri, COUNT(s.ip))
            FROM Stat AS s
            WHERE s.timestamp BETWEEN :start AND :end
            GROUP BY s.ip, s.uri
            ORDER BY COUNT (s.ip) DESC
            """)
    List<ResponseDto> findAllWithoutUris(LocalDateTime start,
                                         LocalDateTime end);

}