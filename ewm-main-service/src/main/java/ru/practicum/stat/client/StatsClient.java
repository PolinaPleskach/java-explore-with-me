package ru.practicum.stat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.RequestDto;
import ru.practicum.ResponseDto;

import java.util.List;

@FeignClient(value = "stats-client", url = "http://stats-server:9090")
public interface StatsClient {

    @PostMapping("/hit")
    RequestDto createStats(@RequestBody RequestDto creationDto);

    @GetMapping("/stats")
    List<ResponseDto> getStats(@RequestParam String start,
                               @RequestParam String end,
                               @RequestParam(required = false) String[] uris,
                               @RequestParam(defaultValue = "false") boolean unique);
}