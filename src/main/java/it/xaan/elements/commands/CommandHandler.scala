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

import it.xaan.elements.Settings
import it.xaan.elements.database.Postgres
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
// I hate this class.

class CommandHandler(initial: Command[_, _]*)(implicit val database: Postgres, settings: Settings)
    extends ListenerAdapter {

  private var commands = mutable.Map[String, Command[_, _]]()
  register(initial: _*)
  def register(commands: Command[_, _]*): Unit =
    commands.foreach { command =>
      this.commands += command.name.toLowerCase -> command
      command.aliases.foreach(alias => this.commands += alias.toLowerCase -> command)
    }

  def unregister(commands: Command[_, _]*): Unit =
    commands.foreach { command =>
      this.commands.remove(command.name.toLowerCase)
      command.aliases.foreach(alias => this.commands.remove(alias.toLowerCase))
    }

  override def onGuildMessageReceived(event: GuildMessageReceivedEvent): Unit = {
    val member  = event.getMember
    val message = event.getMessage
    val content = message.getContentRaw
    val guild   = event.getGuild
    val channel = event.getChannel
    if (member.getUser.isBot) return
    if (content.isEmpty) return
    val args = content
      .split(" ")
    this.commands.get(args(0).substring(settings.prefix.length).toLowerCase) match {
      case None => // Doesn't exist. It's gucci
      case Some(command) =>
        database
          .getUser(member.getIdLong)
          .onComplete {
            case Failure(exception) => exception.printStackTrace()
            case Success(user) =>
              if (user.permissions.contains(command.permission)) {
                command.execute(member, guild, database, channel, message) match {
                  case Left(failure) => println(s"${command.name} failed with $failure")
                  case Right(_)      => // We don't care
                }
              }
          }(ExecutionContext.global)
    }
  }
}
