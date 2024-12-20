/**
 * Copyright (C) 2013-2021 Klarna AB
 * Copyright (C) 2021 The HiveRunner Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klarna.hiverunner.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TsvFileParserTest {

    @Test
    public void parsesTsv() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/data.tsv");
        TsvFileParser tsvFileParser = new TsvFileParser();
        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1", "e1"}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", "c2", "d2", "e2"}, result.get(1));
    }

    @Test
    public void parsesTsvNotEnoughFieldsInFile() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/data.tsv");
        TsvFileParser tsvFileParser = new TsvFileParser();
        Assertions.assertThrows(IllegalStateException.class, () -> {
            tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e", "f"));
        });
    }

    @Test
    public void parsesTsvSubSelectFields() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/data.tsv");
        TsvFileParser tsvFileParser = new TsvFileParser();
        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1"}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", "c2", "d2"}, result.get(1));
    }

    @Test
    public void parsesCsvWithEmptyFields() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/data.csv");
        TsvFileParser tsvFileParser = new TsvFileParser().withDelimiter(",");
        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1", null}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", null, "d2", "e2"}, result.get(1));
    }

    @Test
    public void csvWithCustomNullValue() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/dataWithCustomNullValue.csv");
        TsvFileParser tsvFileParser = new TsvFileParser().withDelimiter(",").withNullValue("NULL");
        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1", null}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", null, "d2", "e2"}, result.get(1));
    }

    @Test
    public void tsvWithHeader() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/dataWithHeader.tsv");
        TsvFileParser tsvFileParser = new TsvFileParser().withHeader();

        assertTrue(tsvFileParser.hasColumnNames());
        assertEquals(tsvFileParser.getColumnNames(dataFile), Arrays.asList("a", "b", "c", "d", "e"));

        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1", "e1"}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", "c2", "d2", "e2"}, result.get(1));
    }

    @Test
    public void csvWithHeader() {
        File dataFile = new File("src/test/resources/TsvFileParserTest/dataWithHeader.csv");
        TsvFileParser tsvFileParser = new TsvFileParser().withDelimiter(",").withHeader();

        assertTrue(tsvFileParser.hasColumnNames());
        assertEquals(tsvFileParser.getColumnNames(dataFile), Arrays.asList("a", "b", "c", "d", "e"));

        List<Object[]> result = tsvFileParser.parse(dataFile, null, Arrays.asList("a", "b", "c", "d", "e"));
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"a1", "b1", "c1", "d1", null}, result.get(0));
        assertArrayEquals(new String[]{"a2", "b2", null, "d2", "e2"}, result.get(1));
    }
}
