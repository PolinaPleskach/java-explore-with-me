package ru.practicum.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}