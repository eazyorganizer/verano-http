package hr.com.vgv.verano.http.request;

import java.util.Map;

import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;

import hr.com.vgv.verano.http.Dict;
import hr.com.vgv.verano.http.DictInput;
import hr.com.vgv.verano.http.HashDict;
import hr.com.vgv.verano.http.Kvp;
import hr.com.vgv.verano.http.KvpOf;

public class Headers extends DictInput.Simple
{
    private static final String KEY = "h.";

    public Headers(final Kvp... kvps) {
        this(new IterableOf<>(kvps));
    }

    public Headers(final Iterable<Kvp> kvps) {
        super((Dict dict) -> new HashDict(
            new Mapped<>(
                input -> new KvpOf(
                    String.format("%s%s", KEY, input.key()), input.value()
                ),
                kvps
            )
        ));
    }

    public static class Of extends IterableEnvelope<Kvp> {

        public Of(final Dict dict)
        {
            super(() -> new Mapped<>(
                in -> new KvpOf(in.key().substring(2), in.value()),
                new Filtered<>(
                    input -> input.key().startsWith(KEY),
                    dict
                )
            ));
        }

        public final Map<String, String> asMap() {
            return new MapOf<>(
                new Mapped<>(
                    input -> new MapEntry<>(input.key(), input.value()),
                    this
                )
            );
        }
    }

}
