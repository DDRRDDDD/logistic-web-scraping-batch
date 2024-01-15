package com.project.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Menu<T> {

    private final String option;
    private final Class<T> pageClass;

}
