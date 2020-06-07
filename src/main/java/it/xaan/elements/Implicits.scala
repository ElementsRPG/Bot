/*
 * CuddlegangBot - A bot for the cuddlegang RPG server.
 * Copyright © 2020 Jacob Frazier (shadowjacob1@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.xaan.elements

object Implicits {

  implicit class AnyMapExtensions(val x: Map[String, Any]) extends AnyVal {
    def boolean(y: String): Boolean = x(y).asInstanceOf[Boolean]
    def char(y: String): Char       = x(y).asInstanceOf[Char]
    def int(y: String): Int         = x(y).asInstanceOf[Int]
    def long(y: String): Long       = x(y).asInstanceOf[Long]
    def float(y: String): Float     = x(y).asInstanceOf[Float]
    def short(y: String): Short     = x(y).asInstanceOf[Short]
    def string(y: String): String   = x(y).asInstanceOf[String]
    def double(y: String): Double   = x(y).asInstanceOf[Double]
    def byte(y: String): Byte       = x(y).asInstanceOf[Byte]
  }
}
