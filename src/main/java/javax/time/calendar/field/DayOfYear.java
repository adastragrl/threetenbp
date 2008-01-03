/*
 * Copyright (c) 2007,2008, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.calendar.field;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceArray;

import javax.time.calendar.Calendrical;
import javax.time.calendar.CalendricalState;
import javax.time.calendar.IllegalCalendarFieldValueException;
import javax.time.calendar.TimeFieldRule;

/**
 * A calendrical representation of a day of year.
 * <p>
 * DayOfYear is an immutable time field that can only store a day of year.
 * It is a type-safe way of representing a day of year in an application.
 * <p>
 * Static factory methods allow you to construct instances.
 * The day of year may be queried using getDayOfYear().
 * <p>
 * DayOfYear is thread-safe and immutable.
 *
 * @author Stephen Colebourne
 */
public final class DayOfYear implements Calendrical, Comparable<DayOfYear>, Serializable {

    /**
     * The rule implementation that defines how the day of year field operates.
     */
    public static final TimeFieldRule RULE = new Rule();
    /**
     * A serialization identifier for this instance.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Cache of singleton instances.
     */
    private static final AtomicReferenceArray<DayOfYear> cache = new AtomicReferenceArray<DayOfYear>(367);

    /**
     * The day of year being represented.
     */
    private final int dayOfYear;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of <code>DayOfYear</code>.
     *
     * @param dayOfYear  the day of year to represent
     * @return the created DayOfYear
     * @throws IllegalCalendarFieldValueException if the dayOfYear is invalid
     */
    public static DayOfYear dayOfYear(int dayOfYear) {
        try {
            DayOfYear result = cache.get(dayOfYear);
            if (result == null) {
                DayOfYear temp = new DayOfYear(dayOfYear);
                cache.compareAndSet(dayOfYear, null, temp);
                result = cache.get(dayOfYear);
            }
            return result;
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalCalendarFieldValueException(
                RULE.getName(), dayOfYear, RULE.getMinimumValue(), RULE.getMaximumValue());
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Constructs an instance with the specified day of year.
     *
     * @param dayOfYear  the day of year to represent
     */
    private DayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    /**
     * Resolve the singleton.
     *
     * @return the singleton, never null
     */
    private Object readResolve() {
        return dayOfYear(dayOfYear);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the day of year value.
     *
     * @return the day of year
     */
    public int getValue() {
        return dayOfYear;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the calendrical state which provides internal access to this
     * DayOfYear instance.
     *
     * @return the calendar state for this instance, never null
     */
    public CalendricalState getCalendricalState() {
        return null;  // TODO
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this day of year instance to another.
     *
     * @param otherDayOfYear  the other day of year instance, not null
     * @return the comparator value, negative if less, postive if greater
     * @throws NullPointerException if otherDayOfYear is null
     */
    public int compareTo(DayOfYear otherDayOfYear) {
        int thisValue = this.dayOfYear;
        int otherValue = otherDayOfYear.dayOfYear;
        return (thisValue < otherValue ? -1 : (thisValue == otherValue ? 0 : 1));
    }

    /**
     * Is this day of year instance greater than the specified day of year.
     *
     * @param otherDayOfYear  the other day of year instance, not null
     * @return true if this day of year is greater
     * @throws NullPointerException if otherDayOfYear is null
     */
    public boolean isGreaterThan(DayOfYear otherDayOfYear) {
        return compareTo(otherDayOfYear) > 0;
    }

    /**
     * Is this day of year instance less than the specified day of year.
     *
     * @param otherDayOfYear  the other day of year instance, not null
     * @return true if this day of year is less
     * @throws NullPointerException if otherDayOfYear is null
     */
    public boolean isLessThan(DayOfYear otherDayOfYear) {
        return compareTo(otherDayOfYear) < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Is this instance equal to that specified, evaluating the day of year.
     *
     * @param otherDayOfYear  the other day of year instance, null returns false
     * @return true if the day of year is the same
     */
    @Override
    public boolean equals(Object otherDayOfYear) {
        if (this == otherDayOfYear) {
            return true;
        }
        if (otherDayOfYear instanceof DayOfYear) {
            return dayOfYear == ((DayOfYear) otherDayOfYear).dayOfYear;
        }
        return false;
    }

    /**
     * A hashcode for the day of year object.
     *
     * @return a suitable hashcode
     */
    @Override
    public int hashCode() {
        return dayOfYear;
    }

    /**
     * A string describing the day of year object.
     *
     * @return a string describing this object
     */
    @Override
    public String toString() {
        return "DayOfYear=" + getValue();
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of the rules for the day of year field.
     */
    private static class Rule extends TimeFieldRule {

        /** Constructor. */
        protected Rule() {
            super("DayOfYear", null, null, 1, 366);
        }

        /** {@inheritDoc} */
        @Override
        public int getValue(CalendricalState calState) {
            return super.getValue(calState) + 1;
        }
    }

}
