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

// Potential bug, there is no current way to replace {} with {}. Workaround is a ZWS.
/**
 * A class for various formatting tasks. Not as expansive as String.format, nicer to use.
 */
public final class Formatter {

  // We don't need people creating an instance of the class
  // and to avoid reflection, we throw an IllegalStateException
  // if someone tries anyway.
  private Formatter() {
    throw new IllegalStateException("No formatter instances may be created!");
  }

  /**
   * Formats the specified string based on the index of the arguments passed.
   * This replaces {} such that {@code Formatter.format("{} {}.", "Hello", "World") }
   * will return {@code Hello World.} All arguments will be passed as their {@link Object#toString}
   * representations.
   * <br>
   * Additional arguments will be ignored, and passing too few arguments wll leave ones that can't
   * be replaced inside the string.
   *
   * @param str  The string to format.
   * @param args The arguments to format with.
   * @return The formatted String.
   */
  public static String format(String str, final Object... args) {
    for (final Object arg : args) {
      if (!str.contains("{}")) {
        break;
      }
      str = str.replaceFirst("\\{}", arg.toString());
    }
    return str;
  }

  /**
   * Formats the specified string based on the index of the arguments passed.
   * This replaces such that {@code Formatter.format("{hello} {world}.",
   * Map.ofEntries(Map.entry("hello", "Hello"), Map.entry("world", "World"))); }
   * will return {@code Hello World.} All arguments will be passed as their {@link Object#toString}
   * representations.
   * <br>
   * Additional arguments will be ignored, and passing too few arguments wll leave ones that can't
   * be replaced inside the string.
   *
   * @param str  The string to format.
   * @param args The arguments to format with.
   * @return The formatted String.
   */
  public static String format(String str, final Map<String, Object> args) {
    for (final Map.Entry<String, Object> entry : args.entrySet()) {
      final String key = format("{{}}", entry.getKey());
      if (!str.contains(key)) {
        break;
      }
      //
      str = str.replaceFirst(key.replaceFirst("\\{", "\\\\{"), entry.getValue().toString());
    }
    return str;
  }
}
