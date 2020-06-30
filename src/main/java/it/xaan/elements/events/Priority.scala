/*
 * Elements - A bot for the Elements RPG server.
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
package it.xaan.elements.events

import enumeratum._

sealed trait Priority extends EnumEntry with Ordered[Priority] {
  override def compare(that: Priority): Int = Priority.values.indexOf(this) - Priority.values.indexOf(that)
}
object Priority extends Enum[Priority] {
  val values: IndexedSeq[Priority] = findValues
  case object First    extends Priority
  case object VeryHigh extends Priority
  case object High     extends Priority
  case object Normal   extends Priority
  case object Low      extends Priority
  case object VeryLow  extends Priority
  case object Last     extends Priority
}
