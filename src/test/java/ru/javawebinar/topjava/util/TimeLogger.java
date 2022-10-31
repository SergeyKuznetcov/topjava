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
    public static final Stopwatch TEST_TIME = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String testResult = "Test " + description.getDisplayName() + " finished in " + TimeUnit.NANOSECONDS.toMillis(nanos) + " ms";
            result.append(testResult);
            result.append("\n");
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
            log.info(result.toString());
        }
    };
}
