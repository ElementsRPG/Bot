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
package it.xaan.elements.database

import it.xaan.elements.Settings
import it.xaan.elements.database.data._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.global
import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Represents a connection to a postgres database. This is a wrapper around it.
  */
class Postgres()(implicit settings: Settings, context: ExecutionContextExecutor = global) {

  private val db = Database.forURL(
    s"jdbc:postgresql:${settings.postgresTable}",
    driver = "org.postgresql.Driver",
    user = settings.postgresUsername,
    password = settings.postgresPassword
  )

  import it.xaan.elements.database.Postgres._

  private val setup =
    Users.schema.createIfNotExists

  db.run(setup)

  def getUser(id: Long): Future[User] =
    db.run[User](
      Users
        .filter(_.id === id)
        .result
        .head
    )

  def getUsers(ids: Set[Long]): Future[Seq[User]] = db.run(Users.filter(_.id inSet ids).result)

  def save(user: User): Future[Int] =
    db.run(Users.insertOrUpdate(user))

  def delete(user: User): Future[Boolean] =
    db.run(Users.filter(_.id === user.id).delete).map(_ > 0)

  def execute[T](dbio: DBIO[T]): Future[T] = db.run(dbio)

}

object Postgres {
  val Users = TableQuery[UserTable]
}
