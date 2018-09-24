package com.adidas.travel.service.utils;

import com.adidas.travel.RouteTestFactory;
import com.adidas.travel.domain.Route;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TimeUtilsTest {

    @Test
    public void plainTimeTest() {

        Route route = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "02:39:16",
                "04:50:21");

        String r = TimeUtils.parse(Duration.between(route.getDepartureTime(), route.getArrivalTime()));

        Assert.assertEquals(r, "02:11:05");
    }

    @Test
    public void zeroSecTimeTest() {

        Route route = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "03:30:00",
                "04:30:00");

        String r = TimeUtils.parse(Duration.between(route.getDepartureTime(), route.getArrivalTime()));

        Assert.assertEquals(r, "01:00:00");
    }

    @Test
    public void zeroMinTimeTest() {

        Route route = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "03:00:12",
                "04:00:24");

        String r = TimeUtils.parse(Duration.between(route.getDepartureTime(), route.getArrivalTime()));

        Assert.assertEquals(r, "01:00:12");
    }

    @Test
    public void zeroHourTimeTest() {

        Route route = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "00:30:40",
                "02:34:50");

        String r = TimeUtils.parse(Duration.between(route.getDepartureTime(), route.getArrivalTime()));

        Assert.assertEquals(r, "02:04:10");
    }

    @Test
    public void complexTimeTest() {

        Route route = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "22:30:40",
                "02:34:50");

        Duration d = Duration.between(route.getDepartureTime(), route.getArrivalTime());

        d = d.plusHours(24);

        String r = TimeUtils.parse(d);

        Assert.assertEquals(r, "04:04:10");
    }

}