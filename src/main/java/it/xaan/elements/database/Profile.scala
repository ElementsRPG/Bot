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
package it.xaan.elements.database

import it.xaan.elements.database.annotation.{Column, Ignore, Qualifiers, Table}
import it.xaan.elements.database.data.Element
import scalikejdbc.{SQLSyntaxSupport, WrappedResultSet}

/**
  * Represents a player's profile.
  *
  *
  * @param id The discord ID of the user.
  * @param level The level of the user's character
  * @param element The element of the user.
  * @param good If the user is considered good or not.
  * @param gold How much gold the user has.
  * @param _weapon The ID of the weapon the user wields.
  * @param _finisher The ID of the finisher the user has.
  * @param _pet The ID of the type of pet the user has.
  * @param database The database.
  */
@Table(name = "profiles")
case class Profile(
    @Qualifiers(extra = "PRIMARY KEY UNIQUE NOT NULL")
    id: Long,
    level: Int,
    @Column(clazz = classOf[String])
    element: Element,
    good: Boolean,
    gold: Long,
    private val _weapon: Int,
    private val _finisher: Int,
    private val _pet: Int,
    @Ignore
    private val database: Database
)

object Profile extends SQLSyntaxSupport[Profile] {

  override val tableName = "profiles"

  def apply(rs: WrappedResultSet, database: Database): Profile =
    new Profile(
      id = rs.long("id"),
      level = rs.int("level"),
      element = Element.withNameInsensitive(rs.string("element")),
      good = rs.boolean("good"),
      gold = rs.long("gold"),
      _weapon = rs.int("weapon"),
      _finisher = rs.int("finisher"),
      _pet = rs.int("pet"),
      database = database
    )

  import it.xaan.elements.Implicits.AnyMapExtensions

  def apply(rs: Map[String, Any], database: Database): Profile =
    new Profile(
      id = rs.long("id"),
      level = rs.int("level"),
      element = Element.withNameInsensitive(rs.string("element")),
      good = rs.boolean("good"),
      gold = rs.long("gold"),
      _weapon = rs.int("weapon"),
      _finisher = rs.int("finisher"),
      _pet = rs.int("pet"),
      database = database
    )
}
