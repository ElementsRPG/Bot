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

import it.xaan.elements.database.Postgres
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

case class User(
    permissions: Set[PermissionLevel],
    element: Element,
    clazz: Class,
    subclass: Subclass,
    weaponID: Long,
    offhandID: Long,
    secondaryID: Long,
    armorID: Long,
    finisherID: Long,
    gold: Long,
    petID: Long,
    id: Long,
    name: String
) {
  // TOOD: ALL OF THESE
  def weapon()(implicit database: Postgres): Option[Nothing]    = None
  def offhand()(implicit database: Postgres): Option[Nothing]   = None
  def secondary()(implicit database: Postgres): Option[Nothing] = None
  def armor()(implicit database: Postgres): Option[Nothing]     = None
  def finisher()(implicit database: Postgres): Option[Nothing]  = None
  def pet()(implicit database: Postgres): Option[Nothing]       = None
}

class UserTable(tag: Tag) extends Table[User](tag, "users") {

  implicit val PermissionLevelMapper: JdbcType[Set[PermissionLevel]] with BaseTypedType[Set[PermissionLevel]] =
    MappedColumnType.base[Set[PermissionLevel], Int](PermissionLevel.getPermissionSet, PermissionLevel.getPermissions)

  implicit val ElementMapper: JdbcType[Element] with BaseTypedType[Element] =
    MappedColumnType.base[Element, String](_.entryName, Element.withNameInsensitive)

  implicit val ClassMapper: JdbcType[Class] with BaseTypedType[Class] =
    MappedColumnType.base[Class, String](_.entryName, Class.withNameInsensitive)

  implicit val SubclassMapper: JdbcType[Subclass] with BaseTypedType[Subclass] =
    MappedColumnType.base[Subclass, String](_.entryName, Subclass.withNameInsensitive)

  override def * =
    (permissions, element, clazz, subclass, weapon, offhand, secondary, armor, finisher, gold, pet, id, name)
      .mapTo[User]

  def permissions = column[Set[PermissionLevel]]("permissions")
  def element     = column[Element]("element")
  def clazz       = column[Class]("class")
  def subclass    = column[Subclass]("subclass")
  def weapon      = column[Long]("weapon")
  def offhand     = column[Long]("offhand")
  def secondary   = column[Long]("secondary")
  def armor       = column[Long]("armor")
  def finisher    = column[Long]("finisher")
  def gold        = column[Long]("gold")
  def pet         = column[Long]("pet")
  def id          = column[Long]("id", O.Unique, O.PrimaryKey)
  def name        = column[String]("name")
}
