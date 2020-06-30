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

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

/**
  * Represents a player's profile.
  *
  * @param id The discord ID of the user.
  * @param exp The exp of the user's character
  * @param element The element of the user.
  * @param good If the user is considered good or not.
  * @param gold How much gold the user has.
  * @param weaponID The ID of the weapon the user wields.
  * @param finisherID The ID of the finisher the user has.
  * @param petID The ID of the type of pet the user has.
  */
case class Profile(
    id: Long,
    exp: Long,
    element: Element,
    good: Boolean,
    gold: Long,
    // Todo: FIX
    private[database] val weaponID: Int,
    private[database] val finisherID: Int,
    private[database] val petID: Int
) extends Tabled {}

class ProfileTable(tag: Tag) extends Table[Profile](tag, "profiles") {

  implicit val ElementMapper: JdbcType[Element] with BaseTypedType[Element] =
    MappedColumnType.base[Element, String](_.entryName, Element.withNameInsensitive)

  override def * = (id, exp, element, good, gold, weaponID, finisherID, petID).mapTo[Profile]

  def id = column[Long]("id", O.PrimaryKey, O.Unique)

  def exp = column[Long]("exp")

  def element = column[Element]("element")

  def good = column[Boolean]("good")

  def gold = column[Long]("gold")

  def weaponID = column[Int]("weaponID")

  def finisherID = column[Int]("finisherID")

  def petID = column[Int]("petID")
}
