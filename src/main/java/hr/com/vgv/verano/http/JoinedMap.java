package hr.com.vgv.verano.http;

import java.util.Map;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapEnvelope;
import org.cactoos.map.MapOf;

public class JoinedMap<K, V> extends MapEnvelope<K, V>
{
    public JoinedMap(Map.Entry<K, V> entry, Map<K, V> map) {
        this(new MapOf<>(entry), map);
    }

    @SafeVarargs
    public JoinedMap(Map<K, V>... maps)
    {
        super(
            () -> new MapOf<>(
                new Joined<>(
                    new Mapped<>(
                        input -> input.entrySet(),
                        maps
                    )
                )
            )
        );
    }
}
