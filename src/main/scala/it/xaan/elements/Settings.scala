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

import com.typesafe.config.Config

class Settings(config: Config) {
  config.checkValid(config)

  val token: String            = config.getString("token")
  val postgresPassword: String = config.getString("postgres_password")
  val postgresTable: String    = config.getString("postgres_table")
  val postgresUsername: String = config.getString("postgres_username")
  val dev: Boolean             = config.getBoolean("dev")
  val gameMaster: Long         = config.getObject("roles").toConfig.getLong("game-master")
  val prefix: String           = config.getString("prefix")
}
