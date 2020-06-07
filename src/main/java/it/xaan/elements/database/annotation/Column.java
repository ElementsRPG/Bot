/*
 * CuddlegangBot - A bot for the cuddlegang RPG server.
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
package it.xaan.elements.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a column for a postgres table.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Column {

  /**
   * The name of the column, if it differs from the parameter name. Default is {@code ""}. This is
   * the exact same as not having the annotation at all.
   *
   * @return The name of the column.
   */
  String name() default "";

  /**
   * The {@link Class} that this column represents in Scala. See {@link
   * it.xaan.elements.database.Database#postgresTypes()} for the mappings. This is for when you have a type in your POJO
   * that differs from the postgres type. Like representing something as an enum in your code but an
   * integer in your database. This must always be specified as it has no meaningful default that
   * java allows.
   *
   * @return The class this column represents in Scala.
   */
  Class<?> clazz();

  /**
   * A manual override for the column if you need it, for instance having an Array type in your
   * database since the JVM's generics are erased at runtime.
   *
   * @return The override to use for anything else.
   */
  String override() default "";
}
