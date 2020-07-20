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
package it.xaan.elements.events

case class Dispatcher private (listeners: Seq[Listener[_]]) {
  def dispatch(event: Event): Unit = {
    var cancelled = false
    listeners.foreach(_.receiveCast(event, cancelled) match {
      case Result.Cancelled => cancelled = true
      case _                => // ignored
    })
  }

}

object Dispatcher {
  def apply(events: Seq[Listener[_]]): Dispatcher = new Dispatcher(events.sortBy(_.priority))
}