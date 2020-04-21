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
package it.xaan.cuddlegang.util;

import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public final class FormatterTest {

  private final static String CONTROL = "Hello World.";

  @Test
  public void formatVarargs() {
    // Exact number
    Assert.assertEquals(CONTROL,
        Formatter.format("{} {}.", "Hello", "World"));
    // Too many
    Assert.assertEquals(CONTROL,
        Formatter.format("{} {}.", "Hello", "World", "Random", "Arguments"));
    // Too few.
    Assert.assertEquals("Hello {}.", Formatter.format("{} {}.", "Hello"));
  }

  // TODO: Fix this test, finding the wrong thing.
  @Test
  public void formatMap() {
    // Exact number
    Assert.assertEquals(CONTROL,
        Formatter.format("{hello} {world}.",
            Map.ofEntries(Map.entry("hello", "Hello"), Map.entry("world", "World"))));
    // Too many
    Assert.assertEquals(CONTROL,
        Formatter.format("{hello} {world}.",
            Map.ofEntries(Map.entry("hello", "Hello"), Map.entry("world", "World"),
                Map.entry("random", "random"))));
    // Too few.
    Assert.assertEquals("Hello {world}.",
        Formatter.format("{hello} {world}.", Map.ofEntries(Map.entry("hello", "Hello"))));
  }
}
