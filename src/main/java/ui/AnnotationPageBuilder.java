package ui;

import util.Annotations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationPageBuilder {

    public static List<MenuItem> buildPageItems(Page page) {
        return Arrays.stream(page.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Annotations.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(Annotations.class).order()))
                .map(m -> {
                    Annotations annotation = m.getAnnotation(Annotations.class);
                    m.setAccessible(true);
                    return new MenuItem(
                            annotation.name(),
                            annotation.right(),
                            () -> {
                                try {
                                    return (ui.Page) m.invoke(page);
                                } catch (Exception e) {
                                    throw new RuntimeException("Menu action failed: " + m.getName(), e);
                                }
                            }
                    );
                })
                .collect(Collectors.toList());
    }
}