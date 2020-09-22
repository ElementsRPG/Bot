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
  * Represents a user's permission level in the bot aka how much they can do.
  */
sealed abstract class PermissionLevel(val _mask: Int) extends EnumEntry {
  val mask: Int = 1 << _mask
}

case object PermissionLevel extends Enum[PermissionLevel] {
  val values: IndexedSeq[PermissionLevel] = findValues

  def getPermissions(x: Int): Set[PermissionLevel] =
    values.filter(perm => (x & perm.mask) != 0).toSet

  @scala.annotation.tailrec
  def getPermissionSet(perms: Set[PermissionLevel]): Int =
    if (perms.contains(Root) && perms.size != values.length) getPermissionSet(values.toSet)
    else if (!perms.contains(User)) getPermissionSet(perms + User)
    else perms.foldLeft(0)(_ ^ _.mask)

  /**
    * A standard user. Everyone.
    */
  case object User extends PermissionLevel(1)

  /**
    * A discord admin, but not a game master.
    */
  case object Admin extends PermissionLevel(2)

  /**
    * A user who manipulates the game.
    */
  case object GameMaster extends PermissionLevel(3)

  /**
    * A bot developer.
    */
  case object Developer extends PermissionLevel(4)

  /**
    *  Full bot control
    */
  case object Root extends PermissionLevel(5)

}
