package com.mapfre.test.hooks;

import com.mapfre.exceptions.FrameworkException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 + * Stores data that belongs to one Cucumber scenario.
 + *
 + * Cucumber PicoContainer creates one instance for each scenario and injects that same instance
 + * into every hook and step-definition object used by that scenario. Parallel scenarios therefore
 + * use different {@code ScenarioContext} instances.
 + *
 + * This class intentionally uses a regular {@link HashMap}: it is safe because Cucumber executes
 + * the steps of one scenario sequentially and the map is confined to that scenario. Do not store
 + * this object in a static field or access it from asynchronously-created threads.
 + */
public class ScenarioContext {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^{}]+)}");
    private final Map<String, Object> data = new HashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key));
    }

    public String getString(String key) {
        Object value = data.get(key);
        if (value == null) {
            throw new FrameworkException("No existe el dato '" + key + "' en el contexto del escenario");
        }
        return String.valueOf(value);
    }
    public String resolvePlaceholders(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(value);
        StringBuffer resolved = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(resolved, Matcher.quoteReplacement(getString(matcher.group(1).trim())));
        }
        matcher.appendTail(resolved);
        return resolved.toString();
    }
}
