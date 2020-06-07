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
package it.xaan.elements

import enumeratum._

// TODO: Bitset maybe? I need a way to represent multiple 'roles'
/**
  * Represents a user's permission level in the bot aka how much they can do.
  */
sealed trait PermissionLevel extends EnumEntry

case object PermissionLevel extends Enum[PermissionLevel] {
  val values: IndexedSeq[PermissionLevel] = findValues

  /**
    * A standard user.
    */
  case object User extends PermissionLevel

  /**
    * A discord admin, but not a game master.
    */
  case object Admin extends PermissionLevel

  /**
    * A user who manipulates the game.
    */
  case object GameMaster extends PermissionLevel

  /**
    * A bot developer.
    */
  case object Developer extends PermissionLevel

  /**
    *  Full bot control
    */
  case object Root extends PermissionLevel
}
