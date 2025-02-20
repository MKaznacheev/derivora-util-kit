/*
 * This file is part of Derivora Util Kit.
 *
 * Derivora Util Kit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Derivora Util Kit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Derivora Util Kit. If not, see https://www.gnu.org/licenses/lgpl-3.0.html.
 */

package xyz.derivora.utilkit.numbers;

import java.math.BigDecimal;

class CustomComparableNumber extends CustomNumber implements Comparable<CustomComparableNumber> {

    static final CustomComparableNumber ZERO = new CustomComparableNumber("0");
    static final CustomComparableNumber ONE = new CustomComparableNumber("1");
    static final CustomComparableNumber NEGATIVE_ONE = new CustomComparableNumber("-1");

    CustomComparableNumber(String number) {
        super(number);
    }

    static CustomComparableNumber getIncomparableInstance(String number) {
        return new CustomComparableNumber(number) {
            @Override
            public int compareTo(CustomComparableNumber other) {
                throw new UnsupportedOperationException("Comparison not supported");
            }
        };
    }

    @Override
    public int compareTo(CustomComparableNumber other) {
        BigDecimal thisBigDecimal = new BigDecimal(number);
        BigDecimal otherBigDecimal = new BigDecimal(other.number);
        return thisBigDecimal.compareTo(otherBigDecimal);
    }
}
