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
import it.xaan.elements.database.data.Element._

sealed abstract class Subclass(val element: Element) extends EnumEntry

object Subclass extends Enum[Subclass] {
  val values = findValues

  case object BloodMagic      extends Subclass(Darkness)
  case object CorruptReaper   extends Subclass(Darkness)
  case object StoneTaunt      extends Subclass(Earth)
  case object Protector       extends Subclass(Earth)
  case object BlueFlame       extends Subclass(Fire)
  case object QuickBurn       extends Subclass(Fire)
  case object PackedIce       extends Subclass(Ice)
  case object ShieldingIce    extends Subclass(Ice)
  case object FlowerBlossomer extends Subclass(Life)
  case object RoseThorns      extends Subclass(Life)
  case object Purifier        extends Subclass(Light)
  case object HolyMagic       extends Subclass(Light)
  case object ToxicWeaken     extends Subclass(Plague)
  case object GreaterSickness extends Subclass(Plague)
  case object DazingStrike    extends Subclass(Tempest)
  case object ElectricRain    extends Subclass(Tempest)
  case object TsunamiMaster   extends Subclass(Water)
  case object Riptide         extends Subclass(Water)
  case object StrongerBreeze  extends Subclass(Wind)
  case object QuickReflexes   extends Subclass(Wind)
  case object None            extends Subclass(Element.None)
}
