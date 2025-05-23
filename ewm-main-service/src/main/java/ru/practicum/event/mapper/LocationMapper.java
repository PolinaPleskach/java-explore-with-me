package ru.practicum.event.mapper;

import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.entity.Location;

public class LocationMapper {

    public Location mapToLocation(LocationDto dto) {
        return new Location(
                null,
                dto.getLat(),
                dto.getLon()
        );
    }

    public LocationDto mapToLocationDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }
}