/*
 * Joda Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2004 Stephen Colebourne.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Joda project (http://www.joda.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Joda" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact licence@joda.org.
 *
 * 5. Products derived from this software may not be called "Joda",
 *    nor may "Joda" appear in their name, without prior written
 *    permission of the Joda project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JODA AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Joda project and was originally 
 * created by Stephen Colebourne <scolebourne@joda.org>. For more
 * information on the Joda project, please see <http://www.joda.org/>.
 */
package org.joda.time;

/**
 * Defines a time period specified in terms of individual duration fields
 * such as years and days.
 * <p>
 * The implementation of this interface may be mutable or immutable. This
 * interface only gives access to retrieve data, never to change it.
 * <p>
 * Periods are split up into multiple fields, for example days and seconds.
 * Implementations are not required to evenly distribute the values across the fields.
 * The value for each field may be positive or negative.
 * <p>
 * When a time period is added to an instant, the effect is to add each field in turn.
 * For example, a time period could be defined as 3 months, 2 days and -1 hours.
 * In most circumstances this would be the same as 3 months, 1 day, and 23 hours.
 * However, when adding across a daylight savings boundary, a day may be 23 or 25 hours long.
 * Thus, the time period is always added field by field to the datetime.
 * <p>
 * Periods are independent of chronology, and can only be treated as durations
 * when paired with a time via an interval.
 *
 * @see ReadableDuration
 * @see ReadableInterval
 * @author Brian S O'Neill
 * @author Stephen Colebourne
 * @since 1.0
 */
public interface ReadablePeriod {

    /**
     * Gets the period type that defines which fields are included in the period.
     *
     * @return the period type
     */
    PeriodType getPeriodType();

    /**
     * Gets the number of fields that this period supports.
     *
     * @return the number of fields supported
     */
    int size();

    /**
     * Gets the field type at the specified index.
     *
     * @param index  the index to retrieve
     * @return the field at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    DurationFieldType getFieldType(int index);

    /**
     * Gets the value at the specified index.
     *
     * @param index  the index to retrieve
     * @return the value of the field at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    int getValue(int index);

    /**
     * Gets the value of one of the fields.
     * <p>
     * If the field type specified is not supported by the period then zero
     * is returned.
     *
     * @param field  the field type to query, null returns zero
     * @return the value of that field, zero if field not supported
     */
    int get(DurationFieldType field);

    /**
     * Checks whether the field type specified is supported by this period.
     *
     * @param field  the field to check, may be null which returns false
     * @return true if the field is supported
     */
    boolean isSupported(DurationFieldType field);

    //-----------------------------------------------------------------------
    /**
     * Get this period as an immutable <code>Period</code> object.
     * <p>
     * This will either typecast this instance, or create a new <code>Period</code>.
     * 
     * @return a Duration using the same field set and values
     */
    Period toPeriod();

    /**
     * Get this object as a <code>MutablePeriod</code>.
     * <p>
     * This will always return a new <code>MutablePeriod</code> with the same fields.
     * 
     * @return a MutablePeriod using the same field set and values
     */
    MutablePeriod toMutablePeriod();

    //-----------------------------------------------------------------------
    /**
     * Compares this object with the specified object for equality based
     * on the value and type of each supported field.
     * All ReadablePeriod instances are accepted.
     *
     * @param readablePeriod  a readable period to check against
     * @return true if all the field values and types are equal, false if
     *  not or the period is null or of an incorrect type
     */
    boolean equals(Object readablePeriod);

    /**
     * Gets a hash code for the period that is compatible with the equals method.
     * The hashcode is calculated as follows:
     * <pre>
     *  int total = 17;
     *  for (int i = 0; i < fields.length; i++) {
     *      total = 27 * total + getValue(i);
     *      total = 27 * total + getFieldType(i).hashCode();
     *  }
     *  return total;
     * </pre>
     *
     * @return a hash code
     */
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Gets the value as a String in the style of the ISO8601 duration format.
     * Technically, the output can breach the ISO specification as weeks may be included.
     * <p>
     * For example, "PT6H3M5S" represents 6 hours, 3 minutes, 5 seconds.
     *
     * @return the value as an ISO8601 style string
     */
    String toString();

}
