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

import java.time.Instant

import it.xaan.ap.common.data.ArgumentBuilder
import it.xaan.ap.common.parsing.options.OptionsBuilder
import it.xaan.ap.common.parsing.parsers.NamedParser
import it.xaan.elements.Implicits.FutureExtensions
import it.xaan.elements.Settings
import it.xaan.elements.database.Postgres
import it.xaan.elements.database.data.User
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.{Member, TextChannel}

class Profile()(implicit database: Postgres, parser: NamedParser, settings: Settings)
    extends Command[((TextChannel, Option[User]), Option[Member])](
      name = "profile",
      arg = { event =>
        val content = event.getMessage.getContentRaw
        import it.xaan.elements.commands.Command._
        val userArg = new ArgumentBuilder[Long](CustomTypes.UserType).withName("user").build()
        val parsed = parser
          .parse(
            Seq(
              userArg
            ),
            content,
            new OptionsBuilder().build()
          )
          .get()

        val user = parsed.getOpt(userArg).orElse(event.getMember.getIdLong)

        (
          (event.getChannel, database.getUser(user).awaitOpt()),
          Option(event.getGuild.getMemberById(user))
        )
      },
      execute = {
        case channel ~ user ~ tagged =>
          user match {
            case None =>
              tagged match {
                case Some(value) => channel.sendMessage(s"${value.getAsMention} doesn't have a profile.")
                case None        => channel.sendMessage("That user does not exist.").queue()
              }
            case Some(user) =>
              tagged match {
                case None => database.delete(user)
                case Some(tagged) =>
                  val embed = new EmbedBuilder()
                    .setAuthor(user.name, null, tagged.getUser.getEffectiveAvatarUrl)
                    .setFooter(user.element.entryName, settings.elementPictures(user.element))
                    .setThumbnail(settings.classPictures(user.clazz))
                    .setTimestamp(Instant.now)
                    .setTitle(user.subclass.entryName, "https://www.xaan.it/")
                    .setDescription(s"""```scala
                                       |Weapon:    "${user.weapon.getOrElse("None")}"
                                       |Offhand:   "${user.offhand.getOrElse("None")}"
                                       |Secondary: "${user.secondary.getOrElse("None")}"
                                       |
                                       |Armor:     "${user.armor.getOrElse("None")}"
                                       |
                                       |Finisher:  "${user.finisher.getOrElse("None")}"
                                       |
                                       |Gold:      ${user.gold}
                                       |Pet:       "${user.pet.getOrElse("None")}"
                                       |```
                                       |""".stripMargin)

                  channel.sendMessage(embed.build()).queue()
              }
          }
      }
    )
