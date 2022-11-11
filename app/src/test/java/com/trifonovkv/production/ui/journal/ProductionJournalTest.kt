package com.trifonovkv.production.ui.journal

import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal class ProductionJournalTest {

    private val rubiconDay = 25
    private val rubiconHour = 20
    private val rubiconMinute = 0

    private val now = LocalDateTime.now()
    private val startYear = now.minusYears(1)
    private val startMonth = now.minusMonths(1)
    private val startDay = now.withDayOfMonth(rubiconDay - 1)
    private val startTime = now.withHour(rubiconHour - 1).withMinute(59)
    private val endYear = now.plusYears(1)
    private val endMonth = now.plusMonths(1)
    private val endDay = now.withDayOfMonth(rubiconDay + 1)
    private val endTime = now.withHour(rubiconHour).withMinute(rubiconMinute + 1)

    private fun generateTestDates(): List<Long> {
        var year = startYear
        val testDates = mutableListOf<Long>()

        while (year.year in startYear.year..endYear.year) {
            var month = startMonth
            while (month in startMonth..endMonth) {
                var day = startDay
                while (day in startDay..endDay) {
                    var time = startTime
                    while (time in startTime..endTime) {
                        testDates.add(
                            LocalDateTime.of(
                                year.year,
                                month.monthValue,
                                day.dayOfMonth,
                                time.hour,
                                time.minute
                            ).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        )
                        time = time.plusMinutes(1)
                    }
                    day = day.plusDays(1)
                }
                month = month.plusMonths(1)
            }
            year = year.plusYears(1)
        }

        testDates.add(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())

        return testDates
    }

    @Test
    fun testIsDateInWorkingMonth() {
        val testDates = generateTestDates()

        println("working current")
        testDates.forEach {
            if (isDateInWorkingMonth(it, 0)) println(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        it
                    ), ZoneId.systemDefault()
                )
            )
        }

        println("working last")
        testDates.forEach {
            if (isDateInWorkingMonth(it, -1)) println(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        it
                    ), ZoneId.systemDefault()
                )
            )
        }

    }
}