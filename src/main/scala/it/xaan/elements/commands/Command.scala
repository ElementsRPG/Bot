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

import it.xaan.ap.common.data.Argument
import it.xaan.ap.common.data.parsed.ParsedNameAguments
import it.xaan.elements.PermissionLevel
import it.xaan.elements.PermissionLevel.User
import it.xaan.elements.commands.Command.Arg
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command[T](
    val name: String,
    val permission: PermissionLevel = User,
    val aliases: Seq[String] = Seq(),
    val arg: Arg[T],
    val execute: T => Unit,
    val sub: Seq[Command[_]] = Seq()
) {
  def executeCast(any: Any): Unit = execute(any.asInstanceOf[T])
}
object Command {
  type Arg[T] = GuildMessageReceivedEvent => T
  val UnitArg: Arg[Unit] = { _ => }

  implicit class Extensions[T](val argument: Argument[T]) extends AnyVal {
    def extract()(implicit parsed: ParsedNameAguments): T = parsed.get(argument)
  }
}
