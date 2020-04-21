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
package it.xaan.cuddlegang.database;

import it.xaan.cuddlegang.util.Formatter;
import org.jdbi.v3.core.Jdbi;

public final class Postgres {
  private final Jdbi backing;

  public Postgres(final String host, final int port, final String database) {
    this.backing = Jdbi.create(Formatter.format("jdbc:postgresql://{}:{}/{}", host, port, database));
  }
}
