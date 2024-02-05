package com.gridnine.testing;

import java.util.List;

public interface FlightFilter {

    List<Flight> checkDepInPast(List<Flight> flights);

    List<Flight> checkArrBeforeDep(List<Flight> flights);

    List<Flight> checkGroundTimeLessTwoHours(List<Flight> flights);
}
