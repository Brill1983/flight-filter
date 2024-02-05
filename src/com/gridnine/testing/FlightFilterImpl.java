package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterImpl implements FlightFilter {
    @Override
    public List<Flight> checkDepInPast(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException(
                    "The given flight list is null value - error");
        }
        return flights.stream()
                .filter(flight -> !flight.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> checkArrBeforeDep(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException(
                    "The given flight list is null value - error");
        }
        return flights.stream()
                .filter(flight -> {
                    for (Segment segment : flight.getSegments()) {
                        if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> checkGroundTimeLessTwoHours(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException(
                    "The given flight list is null value - error");
        }

        return flights.stream()
                .filter(flight -> {
                    if (flight.getSegments().size() == 1) { // if flight has only 1 segment -> no checking
                        return true;
                    }
                    long duration = 0L;
                    for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                        duration = duration +
                                Duration.between(
                                        flight.getSegments().get(i).getArrivalDate(),
                                        flight.getSegments().get(i + 1).getDepartureDate()
                                ).getSeconds();
                    }
                    return duration <= 7200L;
                })
                .collect(Collectors.toList());
    }
}
