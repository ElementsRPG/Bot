/*
 * ArgumentParse - Parsing CLI arguments in Java.
 * Copyright © 2020 xaanit (shadowjacob1@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.xaan.ap.common.parsing.parsers.dsl;

import it.xaan.ap.common.data.ArgumentBuilder;
import it.xaan.ap.common.data.parsed.ParsedNameAguments;
import it.xaan.ap.common.parsing.Parser;
import it.xaan.ap.common.parsing.Types;
import it.xaan.ap.common.parsing.parsers.NamedParser;

public final class Testing {
  private final String a;
  private final int b;
  private final boolean c;

  public Testing(
    @Parameter(name = "a") String a,
    @Parameter(name = "b") int b,
    @Parameter(name = "C") boolean c
  ) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public static void main(String[] args) {
    Parser<ParsedNameAguments> parser = new NamedParser();
    String content = "--a=\"Hello world\" --b=1 --c=true";
    Testing test = DSL.from(parser, Testing.class)
      .bindNamed(
        new ArgumentBuilder<>(Types.STRING_TYPE).withName("a").build(),
        new ArgumentBuilder<>(Types.INTEGER_TYPE).withName("b").build(),
        new ArgumentBuilder<>(Types.BOOLEAN_TYPE).withName("c").build()
      )
      .content(content)
      .validate();
    System.out.println("Test: " + test);
  }

  @Override
  public String toString() {
    return "Testing{" +
      "a='" + this.a + '\'' +
      ", b=" + this.b +
      ", c=" + this.c +
      '}';
  }
}
