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

import java.util

import com.typesafe.config.ConfigFactory
import it.xaan.elements.commands.{CommandHandler, Eval, Ping, Roll}
import it.xaan.elements.database.Postgres
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

object Main {

  implicit val config: Settings = new Settings(ConfigFactory.defaultApplication())

  implicit val database: Postgres = new Postgres()

  def main(args: Array[String]): Unit = {
    val token = config.token
    val jda = JDABuilder
      .create(
        token,
        Set(
          GatewayIntent.GUILD_MESSAGES,
          GatewayIntent.GUILD_MEMBERS
        )
      )
      .addEventListeners(new CommandHandler(new Eval, Roll, Ping))
      .setActivity(Activity.playing("lady jade is awesome"))
    jda.build().awaitReady()
  }

  implicit private def toJavaSet[T](o: Set[T]): java.util.Set[T] = {
    val buffer = new util.HashSet[T]()
    o.foreach(buffer.add)
    buffer
  }
}
