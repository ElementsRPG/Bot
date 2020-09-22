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

import it.xaan.elements.database.Postgres
import it.xaan.elements.database.data.PermissionLevel.Root
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

import scala.collection.mutable.ListBuffer

class Test()(implicit db: Postgres)
    extends Command[GuildMessageReceivedEvent](
      name = "test",
      permission = Root,
      arg = { e => e },
      execute = { event =>
        val buffer = ListBuffer[Message]()

        event.getChannel.getIterableHistory
          .stream()
          .forEach(message => buffer.addOne(message))

        val x = buffer.toSeq
          .filter(!_.getAttachments.isEmpty)
          .map(_.getAttachments.get(0))
          .map(attachmment => (attachmment.getFileName, attachmment.getUrl))
          .map {
            case (name, url) =>
              s"""
                 |{
                 | "name": "$name",
                 | "url": "$url"
                 |}
                 |""".stripMargin
          }
          .foldLeft("") { case (accumulator, element) => s"$accumulator,\n$element" }

        event.getChannel.sendMessage(x).queue()
      }
    )
