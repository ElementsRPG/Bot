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
package it.xaan.elements.commands

import java.util.NoSuchElementException

import it.xaan.elements.Settings
import it.xaan.elements.database.Postgres
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

/**
  * Represents
  *
  * @param initial
  * @param database
  * @param settings
  */
class CommandHandler(initial: Command[_]*)(implicit val database: Postgres, settings: Settings)
    extends ListenerAdapter {

  private val commands = initial
    .flatMap(command =>
      (command.aliases :+ command.name)
        .map(_.toLowerCase)
        .map(name => (name, command))
    )
    .toMap

  override def onGuildMessageReceived(event: GuildMessageReceivedEvent): Unit = {
    val member  = event.getMember
    val message = event.getMessage
    val content = message.getContentRaw
    if (member == null || member.getUser.isBot) return
    if (content.isEmpty || !content.startsWith(settings.prefix)) return
    val args = content.split(" ")
    this.commands.get(args(0).substring(settings.prefix.length).toLowerCase).foreach { command =>
      database
        .getUser(member.getIdLong)
        .onComplete {
          case Failure(exception) =>
            exception.printStackTrace()
          case Success(user) =>
            println(
              s"User ${member.getEffectiveName} is trying to execute ${command.name} that requires permission ${command.permission} while they only have ${user.permissions}"
            )
            if (user.permissions.contains(command.permission)) {
              println("executing")
              Try(command.executeCast(command.arg(event))) match {
                case Failure(exception) => exception.printStackTrace()
                case Success(value)     => // Ignore
              }
            }
        }(ExecutionContext.global)
    }
  }
}
