package biblioteca.utils;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class MapUtils {

    public static <T, C> C mapNullComBackup(T value, Function<T, C> function, C backup) {
        return (value != null) ? function.apply(value) : backup;
    }
}
