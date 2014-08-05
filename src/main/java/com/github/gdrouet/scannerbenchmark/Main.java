/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Guillaume DROUET
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.github.gdrouet.scannerbenchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main class that runs JMH.
 */
public class Main {

    /**
     * Number of forks.
     */
    private static final int FORKS = 1;

    /**
     * Number of warmup iterations.
     */
    private static final int WARMUP_ITERATIONS = 5;

    /**
     * Number of measurement iterations.
     */
    private static final int MEASUREMENT_ITERATIONS = 5;

    /**
     * Number of threads.
     */
    private static final int THREADS = 4;

    /**
     * Executes benchmark with ${@link #WARMUP_ITERATIONS}, {@link #MEASUREMENT_ITERATIONS}, {@link #FORKS} and {@link #THREADS}.
     *
     * @param args args
     * @throws Exception if test fails
     */
    public static void main(final String[] args) throws Exception {
        final Options opt = new OptionsBuilder()
                .include(".*" + JmhBenchmark.class.getSimpleName() + ".*")
                .forks(FORKS)
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASUREMENT_ITERATIONS)
                .threads(THREADS)
                .build();

        new Runner(opt).run();
    }
}
