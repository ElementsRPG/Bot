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
package it.xaan.elements.database.data

import java.io.InputStream

import enumeratum._

sealed abstract class Class extends EnumEntry {
  def resource(): InputStream = getClass.getResourceAsStream(s"/profile/classes/${getClass.getName}.png")
}

object Class extends Enum[Class] {
  val values: IndexedSeq[Class] = findValues

  case object Rogue   extends Class
  case object Warrior extends Class
  case object Mage    extends Class
  case object Archer  extends Class
}
