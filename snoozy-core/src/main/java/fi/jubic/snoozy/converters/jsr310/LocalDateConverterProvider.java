package fi.jubic.snoozy.converters.jsr310;

import javax.annotation.Nullable;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

public class LocalDateConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(
            Class<T> aClass,
            Type type,
            Annotation[] annotations
    ) {
        if (!LocalDate.class.equals(aClass)) return null;

        boolean isNullable = Stream.of(annotations)
                .anyMatch(
                        annotation -> Objects.equals(
                                annotation.annotationType(),
                                Nullable.class
                        )
                );

        return new ParamConverter<T>() {
            @Override
            public T fromString(@Nullable String s) {
                if (s == null && isNullable) {
                    return aClass.cast(null);
                }
                return aClass.cast(
                        ParseUtil.parseLocalDate(s)
                );
            }

            @Override
            public String toString(T t) {
                return t.toString();
            }
        };
    }
}