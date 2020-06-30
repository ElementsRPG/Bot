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

import it.xaan.elements.PermissionLevel.Root
import it.xaan.elements.database.Postgres
import it.xaan.elements.{Eval, Settings}
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.{Guild, Member, Message, TextChannel}

class Eval()(implicit override val database: Postgres, settings: Settings)
    extends Command[Throwable, Any](name = "eval", permission = Root) {
  override def execute(
      member: Member,
      guild: Guild,
      database: Postgres,
      channel: TextChannel,
      message: Message
  ): Either[Throwable, Any] =
    Eval[Any](message.getContentRaw.split(" ").tail.mkString(" ")).toEither
      .map(x => channel.sendMessage(new EmbedBuilder().setTitle("Result").setDescription(s"$x").build()).queue())
}
