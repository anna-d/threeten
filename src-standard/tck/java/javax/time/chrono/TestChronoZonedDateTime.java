/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.chrono;

import javax.time.Duration;
import static org.testng.Assert.assertEquals;

import javax.time.LocalDate;
import javax.time.LocalDateTime;
import javax.time.LocalTime;
import javax.time.ZoneId;
import javax.time.ZoneOffset;
import javax.time.calendrical.DateTime;
import javax.time.calendrical.DateTimeAccessor;
import javax.time.calendrical.DateTimeBuilder;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.DateTimeValueRange;
import javax.time.calendrical.PeriodUnit;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test assertions that must be true for all built-in chronologies.
 */
@Test
public class TestChronoZonedDateTime {
    //-----------------------------------------------------------------------
    // regular data factory for names and descriptions of available calendars
    //-----------------------------------------------------------------------
    @DataProvider(name = "calendars")
    Chrono[][] data_of_calendars() {
        return new Chrono[][]{
                    {HijrahChrono.INSTANCE},
                    {ISOChrono.INSTANCE},
                    {JapaneseChrono.INSTANCE},
                    {MinguoChrono.INSTANCE},
                    {ThaiBuddhistChrono.INSTANCE}};
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badWithAdjusterChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            DateTime.WithAdjuster adjuster = new FixedAdjuster(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.with(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, " 
                            + "required: " + codt + ", supplied: " + codt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.with(adjuster);
                assertEquals(result, codt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badPlusAdjusterChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            DateTime.PlusAdjuster adjuster = new FixedAdjuster(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.plus(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, "
                            + "required: " + codt + ", supplied: " + codt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.plus(adjuster);
                assertEquals(result, codt2, "WithAdjuster failed to replace date time");
            }
        }
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badMinusAdjusterChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            DateTime.MinusAdjuster adjuster = new FixedAdjuster(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.minus(adjuster);
                    Assert.fail("WithAdjuster should have thrown a ClassCastException, "
                            + "required: " + codt + ", supplied: " + codt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.minus(adjuster);
                assertEquals(result, codt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badPlusPeriodUnitChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            PeriodUnit adjuster = new FixedPeriodUnit(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.plus(1, adjuster);
                    Assert.fail("PeriodUnit.doAdd plus should have thrown a ClassCastException, " + codt
                            + " can not be cast to " + codt2);
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.plus(1, adjuster);
                assertEquals(result, codt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badMinusPeriodUnitChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            PeriodUnit adjuster = new FixedPeriodUnit(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.minus(1, adjuster);
                    Assert.fail("PeriodUnit.doAdd minus should have thrown a ClassCastException, " + codt.getClass()
                            + " can not be cast to " + codt2.getClass());
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.minus(1, adjuster);
                assertEquals(result, codt2, "WithAdjuster failed to replace date");
            }
        }
    }

    @Test(groups={"tck"}, dataProvider="calendars")
    public void test_badDateTimeFieldChrono(Chrono chrono) {
        LocalDate refDate = LocalDate.of(1900, 1, 1);
        ChronoZonedDateTime codt = chrono.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
        for (Chrono[] clist : data_of_calendars()) {
            Chrono chrono2 = clist[0];
            ChronoZonedDateTime<?> codt2 = chrono2.date(refDate).atTime(LocalTime.MIDDAY).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.UTC);
            DateTimeField adjuster = new FixedDateTimeField(codt2);
            if (chrono != chrono2) {
                try {
                    ChronoZonedDateTime<?> notreached = codt.with(adjuster, 1);
                    Assert.fail("DateTimeField doSet should have thrown a ClassCastException, " + codt.getClass()
                            + " can not be cast to " + codt2.getClass());
                } catch (ClassCastException cce) {
                    // Expected exception; not an error
                }
            } else {
                // Same chronology,
                ChronoZonedDateTime<?> result = codt.with(adjuster, 1);
                assertEquals(result, codt2, "DateTimeField doSet failed to replace date");
            }
        }
    }

    /**
     * FixedAdjusted returns a fixed DateTime in all adjustments.
     * Construct an adjuster with the DateTime that should be returned from doWithAdjustment.
     */
    static class FixedAdjuster implements DateTime.WithAdjuster, DateTime.PlusAdjuster, DateTime.MinusAdjuster {
        private DateTime datetime;

        FixedAdjuster(DateTime datetime) {
            this.datetime = datetime;
        }

        @Override
        public DateTime doWithAdjustment(DateTime ignore) {
            return datetime;
        }

        @Override
        public DateTime doPlusAdjustment(DateTime ignore) {
            return datetime;
        }

        @Override
        public DateTime doMinusAdjustment(DateTime ignore) {
            return datetime;
        }

    }

    /**
     * FixedPeriodUnit returns a fixed DateTime in all adjustments.
     * Construct an FixedPeriodUnit with the DateTime that should be returned from doAdd.
     */
    static class FixedPeriodUnit implements PeriodUnit {
        private DateTime dateTime;
        
        FixedPeriodUnit(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public String getName() {
            return "FixedPeriodUnit";
        }

        @Override
        public Duration getDuration() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDurationEstimated() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isSupported(DateTime dateTime) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <R extends DateTime> R doAdd(R dateTime, long periodToAdd) {
            return (R)this.dateTime;
        }

        @Override
        public <R extends DateTime> PeriodBetween between(R dateTime1, R dateTime2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * FixedDateTimeField returns a fixed DateTime in all adjustments.
     * Construct an FixedDateTimeField with the DateTime that should be returned from doSet.
     */
    static class FixedDateTimeField implements DateTimeField {
        private DateTime dateTime;
        FixedDateTimeField(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public String getName() {
            return "FixedDateTimeField";
        }

        @Override
        public PeriodUnit getBaseUnit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public PeriodUnit getRangeUnit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int compare(DateTimeAccessor dateTime1, DateTimeAccessor dateTime2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DateTimeValueRange range() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean doIsSupported(DateTimeAccessor dateTime) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DateTimeValueRange doRange(DateTimeAccessor dateTime) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long doGet(DateTimeAccessor dateTime) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <R extends DateTimeAccessor> R doSet(R dateTime, long newValue) {
            return (R)this.dateTime;
        }

        @Override
        public boolean resolve(DateTimeBuilder builder, long value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
