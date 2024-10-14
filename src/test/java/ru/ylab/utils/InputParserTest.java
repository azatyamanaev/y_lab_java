package ru.ylab.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputParserTest {

    @Test
    public void testParseCKey() {
        try (final PipedInputStream outInputStream = new PipedInputStream();
             final PrintStream out = new PrintStream(new PipedOutputStream(outInputStream));
             final BufferedReader outReader = new BufferedReader(new InputStreamReader(outInputStream))) {

            System.setOut(out);

            InputParser.parseCKey(new Scanner(new ByteArrayInputStream("c".getBytes(StandardCharsets.UTF_8))));
            String s = outReader.readLine();
            Assertions.assertEquals("Enter 'c' to continue", s);
            System.setOut(System.out);
        } catch (
                IOException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testParseDate() {
        LocalDate date = InputParser.parseDate(new Scanner(new ByteArrayInputStream("2024-10-12".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("2024-10-12", date.toString());
    }

    @Test
    public void testParseFrequencyDaily() {
        String res = InputParser.parseFrequency(new Scanner(new ByteArrayInputStream("1".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("DAILY", res);
    }

    @Test
    public void testParseFrequencyWeekly() {
        String res = InputParser.parseFrequency(new Scanner(new ByteArrayInputStream("2".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("WEEKLY", res);
    }

    @Test
    public void testParseFrequencyMonthly() {
        String res = InputParser.parseFrequency(new Scanner(new ByteArrayInputStream("3".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("MONTHLY", res);
    }

    @Test
    public void testParseRoleUser() {
        String res = InputParser.parseRole(new Scanner(new ByteArrayInputStream("1".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("USER", res);
    }

    @Test
    public void testParseRoleAdmin() {
        String res = InputParser.parseRole(new Scanner(new ByteArrayInputStream("2".getBytes(StandardCharsets.UTF_8))));
        Assertions.assertEquals("ADMIN", res);
    }
}
