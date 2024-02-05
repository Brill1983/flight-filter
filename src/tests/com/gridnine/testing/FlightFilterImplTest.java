package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterImplTest {

    FlightFilter flightFilter;
    List<Flight> flights;
    Flight goodFlight1;
    Flight goodFlight2;

    @BeforeEach
    void setUp() {
        flightFilter = new FlightFilterImpl();
        goodFlight1 = new Flight(List.of(new Segment(
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(3))));
        goodFlight2 = new Flight(List.of(new Segment(
                LocalDateTime.now().plusHours(4),
                LocalDateTime.now().plusHours(6))));
    }

    @Test
    void checkDepInPastShouldReturnListOfTwoFlights() {
        flights = List.of(goodFlight1, goodFlight2);
        List<Flight> flightList = flightFilter.checkDepInPast(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
    }

    @Test
    void checkDepInPastShouldReturnZeroLengthListIfFlightListIsEmpty() {
        List<Flight> emptyFlightList = Collections.emptyList();
        List<Flight> flightList = flightFilter.checkDepInPast(emptyFlightList);

        assertEquals(0, flightList.size(), "Should return list size = 0");
    }

    @Test
    void checkDepInPastShouldReturnExceptionIfFlightListIsNull() {
        try {
            flightFilter.checkDepInPast(null);
        } catch (IllegalArgumentException thrown) {
            assertEquals("The given flight list is null value - error", thrown.getMessage(),
                    "Should throw exception with message: 'The given flight list is null value - error'");
        }
    }

    @Test
    void checkDepInPastShouldFilterBadFlights() {

        Flight badFlight = new Flight(List.of(new Segment(
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(3))));
        flights = List.of(goodFlight1, goodFlight2, badFlight);

        List<Flight> flightList = flightFilter.checkDepInPast(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
        assertEquals(goodFlight1.getSegments().get(0).getDepartureDate(),
                flightList.get(0).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of first flight should be equal");
        assertEquals(goodFlight2.getSegments().get(0).getDepartureDate(),
                flightList.get(1).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of second flight should be equal");
    }

    @Test
    void checkArrBeforeDepShouldReturnListOfTwoFlights() {
        flights = List.of(goodFlight1, goodFlight2);
        List<Flight> flightList = flightFilter.checkArrBeforeDep(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
    }

    @Test
    void checkArrBeforeDepShouldReturnZeroLengthListIfFlightListIsEmpty() {
        List<Flight> emptyFlightList = Collections.emptyList();
        List<Flight> flightList = flightFilter.checkArrBeforeDep(emptyFlightList);

        assertEquals(0, flightList.size(), "Should return list size = 0");
    }

    @Test
    void checkArrBeforeDepShouldReturnExceptionIfFlightListIsNull() {
        try {
            flightFilter.checkArrBeforeDep(null);
        } catch (IllegalArgumentException thrown) {
            assertEquals("The given flight list is null value - error", thrown.getMessage(),
                    "Should throw exception with message: 'The given flight list is null value - error'");
        }
    }

    @Test
    void checkArrBeforeDepShouldFilterFlightWithBadSegment() {
        Flight badFlight = new Flight(List.of(new Segment(
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(3))));
        flights = List.of(goodFlight1, goodFlight2, badFlight);

        List<Flight> flightList = flightFilter.checkArrBeforeDep(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
        assertEquals(goodFlight1.getSegments().get(0).getDepartureDate(),
                flightList.get(0).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of first flight should be equal");
        assertEquals(goodFlight2.getSegments().get(0).getDepartureDate(),
                flightList.get(1).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of second flight should be equal");
    }

    @Test
    void checkGroundTimeLessTwoHoursShouldReturnListOfTwoFlights() {
        flights = List.of(goodFlight1, goodFlight2);
        List<Flight> flightList = flightFilter.checkGroundTimeLessTwoHours(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
    }

    @Test
    void checkGroundTimeLessTwoHoursShouldReturnZeroLengthListIfFlightListIsEmpty() {
        List<Flight> emptyFlightList = Collections.emptyList();
        List<Flight> flightList = flightFilter.checkGroundTimeLessTwoHours(emptyFlightList);

        assertEquals(0, flightList.size(), "Should return list size = 0");
    }

    @Test
    void checkGroundTimeLessTwoHoursShouldReturnExceptionIfFlightListIsNull() {
        try {
            flightFilter.checkGroundTimeLessTwoHours(null);
        } catch (IllegalArgumentException thrown) {
            assertEquals("The given flight list is null value - error", thrown.getMessage(),
                    "Should throw exception with message: 'The given flight list is null value - error'");
        }
    }

    @Test
    void checkGroundTimeLessTwoHoursShouldFilterFlightWithGroundTimeMoreThanTwoHours() {
        Flight badFlight = new Flight(List.of(
                new Segment(
                        LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)),
                new Segment(
                        LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4)),
                new Segment(
                        LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(7)
                )));
        flights = List.of(goodFlight1, goodFlight2, badFlight);

        List<Flight> flightList = flightFilter.checkGroundTimeLessTwoHours(flights);

        assertEquals(2, flightList.size(), "Should return list size = 2");
        assertEquals(goodFlight1.getSegments().get(0).getDepartureDate(),
                flightList.get(0).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of first flight should be equal");
        assertEquals(goodFlight2.getSegments().get(0).getDepartureDate(),
                flightList.get(1).getSegments().get(0).getDepartureDate(),
                "Departure DateTime of second flight should be equal");
    }
}