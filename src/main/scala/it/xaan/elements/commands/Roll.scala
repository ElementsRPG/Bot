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

import java.util.concurrent.ThreadLocalRandom

import net.dv8tion.jda.api.entities.TextChannel
case object Roll
    extends Command[TextChannel](
      name = "roll",
      arg = _.getChannel,
      execute = _.sendMessage(s"You rolled a ${ThreadLocalRandom.current().nextInt(10) + 1}/10!").queue()
    )
