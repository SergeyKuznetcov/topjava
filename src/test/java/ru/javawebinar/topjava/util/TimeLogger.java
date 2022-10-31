package ru.javawebinar.topjava.util;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimeLogger extends ExternalResource {
    private static final Logger log = LoggerFactory.getLogger("testsTime");
    private static final StringBuilder result = new StringBuilder();
    private static final String LINE = repeatString("_", 111);
    public static final Stopwatch TEST_TIME = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String testResult = String.format("%-100s %8d ms", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            result.append(testResult).append("\n");
            log.info(testResult);
        }
    };

    public static final ExternalResource ALL_TESTS_TIME = new ExternalResource() {
        @Override
        protected void before() {
            result.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" +
                    LINE +
                    "\nTest name" +
                    repeatString(" ", 94) +
                    "Time, ms\n" +
                    LINE +
                    "\n" +
                    result);

        }
    };

    private static String repeatString(String s, int count) {
        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < count; i++) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
