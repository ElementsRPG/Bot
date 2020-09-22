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
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import scala.util.Try

/**
  * Evaluates scala code.
  */
object EvalWrapper {

  final private val primitives = Seq[Class[_]](
    classOf[Int],
    classOf[Byte],
    classOf[Boolean],
    classOf[Long],
    classOf[Double],
    classOf[Float],
    classOf[Char],
    classOf[Short]
  )

  def apply[A](code: String, context: Map[String, Any] = Map()): Try[A] = {

    val variables = context.iterator
      .map {
        case (key, value) =>
          s"""${"\t"}val $key = context("$key").asInstanceOf[${get(value.getClass)}]"""
      }
      .mkString("\n")

    val result = compile[A](s"""
                               |$variables
                               |implicit def toString[T](x: T): String = s"$$x"
                               |import it.xaan.elements.Implicits.FutureExtensions
                               |$code
                               |""".stripMargin)
    Try(result(context))
  }

  private def get(clazz: Class[_]): String =
    clazz match {
      case x if primitives.contains(x) => clazz.getName.capitalize
      case _                           => clazz.getName
    }

  def compile[A](code: String): Map[String, Any] => A = {
    val formatted = s"""
                       |def wrapper(context: Map[String, Any]): Any = {
                       |${"\t"}$code
                       |}
                       |wrapper _
      """.stripMargin
    val tb        = currentMirror.mkToolBox()
    val tree      = tb.parse(formatted)
    val f         = tb.compile(tree)
    val wrapper   = f()
    wrapper.asInstanceOf[Map[String, Any] => A]
  }

  private def toScalaSet[T](set: java.util.Set[T]): Set[T] = {
    val buffer = ListBuffer[T]()
    set.forEach(buffer.addOne)
    buffer.toSet
  }
}
