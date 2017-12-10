package com.desetude.modularity.additionalmodules;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.BindingAnnotation;
import com.google.inject.Module;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Should annotate a {@link File} to show the file should be the
 * data directory and therefore is injected as such if
 * the {@link BukkitGuiceModule} or any other {@link Module} that has made use of it
 * has been added.
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface DataDir {

}
