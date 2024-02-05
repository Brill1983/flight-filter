package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        FlightFilter filter = new FlightFilterImpl();

        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("_____________initial flights list________________");
        int i = 1;
        for (Flight flight : flights) {
            System.out.println(i + ": " + flight);
            i++;
        }

        System.out.println("_____________filter flights with departure is in the past________________");

        List<Flight> flightList = filter.checkDepInPast(flights);

        i = 1;
        for (Flight flight : flightList) {
            System.out.println(i + ": " + flight);
            i++;
        }

        System.out.println("_____________filter flights with segments arrival before departure________________");

        flightList = filter.checkArrBeforeDep(flights);

        i = 1;
        for (Flight flight : flightList) {
            System.out.println(i + ": " + flight);
            i++;
        }

        System.out.println("_____________filter ground time is more than 2 hours________________");

        flightList = filter.checkGroundTimeLessTwoHours(flights);

        i = 1;
        for (Flight flight : flightList) {
            System.out.println(i + ": " + flight);
            i++;
        }
    }
}