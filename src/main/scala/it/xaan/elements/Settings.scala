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

import com.typesafe.config.{Config, ConfigFactory}
import it.xaan.elements.Implicits.CollectionExtensions
import it.xaan.elements.database.data._
class Settings(private var config: Config) {
  config.checkValid(config)

  def reload(): Unit = config = ConfigFactory.defaultApplication()

  def token: String            = config.getString("token")
  def postgresPassword: String = config.getString("postgres_password")
  def postgresTable: String    = config.getString("postgres_table")
  def postgresUsername: String = config.getString("postgres_username")
  def dev: Boolean             = config.getBoolean("dev")
  def gameMaster: Long         = config.getObject("roles").toConfig.getLong("game-master")
  def prefix: String           = config.getString("prefix")

  def elementPictures: Map[Element, String] = config
    .getObject("pictures")
    .toConfig
    .getObjectList("elements")
    .seq
    .map(_.toConfig)
    .map(config => (Element.withNameInsensitive(config.getString("name")), config.getString("url")))
    .toMap

  def classPictures: Map[Class, String] = config
    .getObject("pictures")
    .toConfig
    .getObjectList("classes")
    .seq
    .map(_.toConfig)
    .map(config => (Class.withNameInsensitive(config.getString("name")), config.getString("url")))
    .toMap

  def elementRoles: Map[Element, Long] = Element.values
    .filter(_ != Element.None)
    .map(element => (element, config.getObject("roles").toConfig.getLong(element.entryName.toLowerCase)))
    .toMap

  def classRoles: Map[Class, Long] = Class.values
    .filter(_ != Class.None)
    .map(clazz => (clazz, config.getObject("roles").toConfig.getLong(clazz.entryName.toLowerCase)))
    .toMap

}
