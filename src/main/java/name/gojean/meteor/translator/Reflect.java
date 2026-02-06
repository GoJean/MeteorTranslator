package name.gojean.meteor.translator;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class Reflect<T> {
    private final T t;
    private final Class<T> cls;

    public Reflect(Class<T> cls, @Nullable T t) {
        this.t = t;
        this.cls = cls;
    }
    public Object getValue(String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(t);
    }
    public void setValue(String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(name);
        field.setAccessible(true);
        field.set(t, value);
    }
    public boolean hasField(String name) {
        for (Field field: cls.getDeclaredFields()) {
            if (field.getName().equals(name)) return true;
        }
        return false;
    }
}
