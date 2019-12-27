package ru.minicom.itphone.client;

import com.automation.remarks.junit5.Video;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Tag(value = "WindowLogin")
@Video
@ParameterizedTest
public @interface ParameterizedTests {}