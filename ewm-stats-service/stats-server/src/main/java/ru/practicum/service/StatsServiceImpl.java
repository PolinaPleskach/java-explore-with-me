package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.RequestDto;
import ru.practicum.ResponseDto;
import ru.practicum.dao.StatsRepository;
import ru.practicum.entity.Stat;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.StatsMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public RequestDto createStat(RequestDto requestDto) {
        Stat stat = statsRepository.save(StatsMapper.INSTANCE.mapToStats(requestDto));
        return StatsMapper.INSTANCE.mapToStatsRequestDto(stat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (start.isAfter(end)) {
            throw new ValidationException("Дата начала и дата окончания не могут быть равны друг другу.");
        }

        if (unique) {
            if (uris != null) {
                return statsRepository.findAllWithUniqueIpWithUris(uris, start, end);
            }
            return statsRepository.findAllWithUniqueIpWithoutUris(start, end);
        } else {
            if (uris != null) {
                return statsRepository.findAllWithUris(uris, start, end);
            }
            return statsRepository.findAllWithoutUris(start, end);
        }
    }
}