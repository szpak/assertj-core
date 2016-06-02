/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.core.api.iterable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.TolkienCharacter.Race.DWARF;
import static org.assertj.core.data.TolkienCharacter.Race.ELF;
import static org.assertj.core.data.TolkienCharacter.Race.HOBBIT;
import static org.assertj.core.data.TolkienCharacter.Race.MAIA;
import static org.assertj.core.data.TolkienCharacter.Race.MAN;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.data.TolkienCharacter;
import org.assertj.core.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IterableAssert_flatExtracting_with_multiple_extractors_Test {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<>();

  private static final Extractor<TolkienCharacter, String> name = new Extractor<TolkienCharacter, String>() {
    @Override
    public String extract(TolkienCharacter input) {
      return input.getName();
    }
  };

  private static final Extractor<TolkienCharacter, Integer> age = new Extractor<TolkienCharacter, Integer>() {
    @Override
    public Integer extract(TolkienCharacter input) {
      return input.getAge();
    }
  };

  @Before
  public void setUp() {
    fellowshipOfTheRing.add(TolkienCharacter.of("Frodo", 33, HOBBIT));
    fellowshipOfTheRing.add(TolkienCharacter.of("Sam", 38, HOBBIT));
    fellowshipOfTheRing.add(TolkienCharacter.of("Gandalf", 2020, MAIA));
    fellowshipOfTheRing.add(TolkienCharacter.of("Legolas", 1000, ELF));
    fellowshipOfTheRing.add(TolkienCharacter.of("Pippin", 28, HOBBIT));
    fellowshipOfTheRing.add(TolkienCharacter.of("Gimli", 139, DWARF));
    fellowshipOfTheRing.add(TolkienCharacter.of("Aragorn", 87, MAN));
    fellowshipOfTheRing.add(TolkienCharacter.of("Boromir", 37, MAN));
  }

  @Test
  public void should_allow_assertions_on_multiple_extracted_values_flattened_in_a_single_list() {
    assertThat(fellowshipOfTheRing).flatExtracting(age, name)
                                   .as("extract ages and names")
                                   .contains(33, "Frodo", 38, "Sam");
  }

  @Test
  public void should_return_empty_list_if_no_extractors_are_specified() {
    assertThat(fellowshipOfTheRing).flatExtracting().isEmpty();
  }

  @Test
  public void should_throw_null_pointer_exception_when_extracting_from_null() {
    thrown.expect(NullPointerException.class);
    fellowshipOfTheRing.add(null);
    assertThat(fellowshipOfTheRing).flatExtracting(age, name);
  }
}