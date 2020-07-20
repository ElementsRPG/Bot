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

import net.dv8tion.jda.api.entities.{Message, TextChannel}

case object Ping
    extends Command[(TextChannel, Message)](
      name = "ping",
      arg = { event => (event.getChannel, event.getMessage) },
      execute = {
        case channel ~ message =>
          def between(m1: Message, m2: Message): Long =
            m1.getTimeCreated.toInstant.toEpochMilli - m2.getTimeCreated.toInstant.toEpochMilli
          channel
            .sendMessage("Pong!")
            .flatMap(msg => msg.editMessage(s"Pong! (${between(msg, message)}ms)"))
            .queue()
      }
    )
