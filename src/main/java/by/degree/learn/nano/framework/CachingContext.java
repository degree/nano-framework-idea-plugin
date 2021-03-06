package by.degree.learn.nano.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingContext extends Context {
    @SuppressWarnings("rawtypes")
    private final Map<Class, Object> CACHE = new ConcurrentHashMap<>();

    public CachingContext(Config config) {
        super(config);
    }

    @Override
    public <T> T getObject(Class<T> target) {
        if (CACHE.containsKey(target)) {
            //noinspection unchecked
            return (T) CACHE.get(target);
        }

        var object = super.getObject(target);

        if (getConfig().isSingleton(getConfig().lookupImplementationClass(target))) {
            CACHE.put(target, object);
        }

        return object;
    }
}
