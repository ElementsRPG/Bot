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

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

object Implicits {
  implicit class CollectionExtensions[T](val collection: java.util.Collection[T]) extends AnyVal {
    private def getBuffer: ListBuffer[T] = {
      val buffer = ListBuffer[T]()
      collection.forEach(element => buffer.addOne(element))
      buffer
    }

    def seq: Seq[T]   = getBuffer.toSeq
    def list: List[T] = getBuffer.toList
    def set: Set[T]   = getBuffer.toSet
  }

  implicit class TExtensions[T](val x: T) extends AnyVal {
    def nil(other: T): T                         = if (x == null) other else x
    def nil[U](mapper: T => U, other: U): U      = if (x == null) other else mapper(x)
    def ?[U >: Null <: AnyRef](other: T => U): U = if (x == null) null else other(x)
  }

  implicit class FutureExtensions[T](val x: Future[T]) extends AnyVal {
    def awaitOpt(duration: Duration = Duration.Inf): Option[T] = awaitTry(duration).toOption

    def awaitTry(duration: Duration = Duration.Inf): Try[T] = Try(await(duration))

    def await(duration: Duration = Duration.Inf): T = Await.result(x, duration)
  }
}
