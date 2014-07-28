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

import eu.infomas.annotation.AnnotationDetector;
import org.openjdk.jmh.annotations.Benchmark;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Benchmark class comparing Reflections to Annotation-Detector.
 */
public class JmhBenchmark {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JmhBenchmark.class);

    /**
     * Scanned annotation.
     */
    private static final Class<? extends Annotation> ANNOTATION =  Configuration.class;

    /**
     * Package.
     */
    private static final String PACKAGE = "org";

    /**
     * <p>
     * Looking for type annotated with an annotation with annotation-detector.
     * </p>
     *
     * @throws Exception if test fails
     */
    @Benchmark
    public void scanAnnotatedTypeAnnotationDetector() throws Exception {
        final AtomicInteger count = new AtomicInteger(0);
        final AnnotationDetector.TypeReporter typeReporter = new AnnotationDetector.TypeReporter() {
            @Override
            public void reportTypeAnnotation(final Class<? extends Annotation> annotation, final String className) {
                count.incrementAndGet();
            }

            @SuppressWarnings("unchecked")
            @Override
            public Class<? extends Annotation>[] annotations() {
                return new Class[] { ANNOTATION };
            }
        };

        final AnnotationDetector cf = new AnnotationDetector(typeReporter);
        cf.detect(PACKAGE);
        LOGGER.info("{} classes annotated with {} retrieved with Annotation-Detector", count.get(), ANNOTATION.getName());
    }

    /**
     * <p>
     * Looking for type annotated with an annotation with Reflections.
     * </p>
     */
    @Benchmark
    public void scanAnnotatedTypeWithReflections() {
        final Collection<Class<?>> r = new Reflections(ClasspathHelper.forPackage(PACKAGE)).getTypesAnnotatedWith(ANNOTATION);
        LOGGER.info("{} classes annotated with {} retrieved with Reflections", r.size(), ANNOTATION.getName());
    }
}
