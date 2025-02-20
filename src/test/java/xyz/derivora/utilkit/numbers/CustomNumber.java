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

class CustomNumber extends Number {

    static final CustomNumber ZERO = new CustomNumber("0");
    static final CustomNumber ONE = new CustomNumber("1");
    static final CustomNumber NEGATIVE_ONE = new CustomNumber("-1");

    static final CustomNumber ILLEGAL_NUMBER = new CustomNumber("0") {
        @Override
        public String toString() {
            return "Not a number value";
        }
    };

    protected final String number;

    CustomNumber(String number) {
        this.number = number;
    }

    @Override
    public int intValue() {
        return Integer.parseInt(number);
    }

    @Override
    public long longValue() {
        return Long.parseLong(number);
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(number);
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(number);
    }

    @Override
    public String toString() {
        return number;
    }
}
