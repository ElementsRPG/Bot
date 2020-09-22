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

import enumeratum._

/**
  * Represents an Element that a character has.
  */
sealed trait Element extends EnumEntry {
  def subclasses: Seq[Subclass] = Subclass.values.filter(_.element == this)
}

object Element extends Enum[Element] {
  val values: IndexedSeq[Element] = findValues

  case object Ice      extends Element
  case object Water    extends Element
  case object Fire     extends Element
  case object Plague   extends Element
  case object Earth    extends Element
  case object Life     extends Element
  case object Tempest  extends Element
  case object Wind     extends Element
  case object Darkness extends Element
  case object Light    extends Element
  case object None     extends Element
}
