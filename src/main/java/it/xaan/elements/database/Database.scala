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

import it.xaan.elements.database.annotation.{Column, Ignore, Qualifiers, Table}
import org.reflections.Reflections
import scalikejdbc._

import scala.util.Try

/**
  * Represents a connection to a postgres database. This is a wrapper around it.
  *
  * @param username The username for the database.
  * @param password The password for the database.
  * @param table The table name.
  */
class Database(
    val username: String = "postgres",
    val password: String = "",
    val table: String = "postgres"
) {

  /**
    * The connection for the database.
    *
    * @return A Try that contains an error or a success depending on if you could connect.
    */
  def connect(): Try[Unit] =
    Try {
      Class.forName("org.postgresql.Driver")
      ConnectionPool.singleton(
        s"jdbc:postgresql://localhost:5432/$table",
        username,
        password
      )
    }

  private[database] implicit val session: AutoSession.type = AutoSession

  private[database] implicit def toList[T](sql: SQL[T, HasExtractor]): List[T] =
    sql.list().apply()

  private[database] implicit def toOption[T](sql: SQL[T, HasExtractor]): Option[T] =
    sql.toOption().apply()

  private[database] implicit def toT[T](sql: SQL[T, HasExtractor]): T =
    sql.apply(0)

  /**
    * Initialises the tables for the classes marked as tables.
    */
  def initialise(): Unit =
    new Reflections("it.xaan.elements")
      .getTypesAnnotatedWith(classOf[Table])
      .forEach(createTable(_))

  // Creates the table for the class.
  private def createTable[T](clazz: Class[T]): Unit = {
    // If we don't have the table annotation, you can just ignore it.
    if (!clazz.isAnnotationPresent(classOf[Table])) return

    val table = clazz.getAnnotation(classOf[Table]).name()

    // We only care about the first constructor anyway.
    val parameters = clazz.getConstructors.head.getParameters
      .filter(!_.isAnnotationPresent(classOf[Ignore]))
      .map(param => {
        val column = param.getAnnotation(classOf[Column])

        // Anything extra, we can just use an empty string if there's nothing extra as it's tacked onto the end
        val extra = param.getAnnotation(classOf[Qualifiers]) match {
          case null  => ""
          case other => other.extra()
        }

        // We strip leading _ from the name
        val name = column match {
          case _ if column != null && column.name() != "" => column.name()
          case _                                          => param.getName.dropWhile(_ == '_')
        }

        // if the column annotation is gone, we just use the param type.
        // Otherwise we use the override if it exists, otherwise we use the
        // column override class
        val `type` = column match {
          case null => Database.postgresTypes(param.getType)
          case _    =>
            column.`override`() match {
              case "" => Database.postgresTypes(column.clazz())
              case _  => column.`override`()
            }
        }

        s"$name ${`type`} $extra"
      })

    val tableQuery =
      s"""
         |CREATE TABLE IF NOT EXISTS $table(
         |${parameters
        .map(x => s"\t$x")
        .mkString(",\n")}
         |);
         |""".stripMargin

    execute(tableQuery)
  }

  /**
    * Executes arbitrary SQL. You should never be calling this unless you're xaanit.
    *
    * @param query The query to run.
    * @return The SQL query.
    */
  def execute(query: String): SQL[Nothing, NoExtractor] = SQL(query)
}

object Database {

  val postgresTypes: Map[Class[_], String] = Map(
    classOf[Int]     -> "SMALLINT",
    classOf[Boolean] -> "BOOLEAN",
    classOf[Char]    -> "CHAR(1)",
    classOf[Byte]    -> "SMALLINT",
    classOf[Short]   -> "SMALLINT",
    classOf[Long]    -> "BIGINT",
    classOf[String]  -> "TEXT"
  )
}
