/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vedran Grgo Vatavuk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package hr.com.vgv.verano.http.parts.body;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.com.vgv.verano.http.Dict;
import hr.com.vgv.verano.http.DictInput;
import hr.com.vgv.verano.http.parts.Body;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Http body as dto object.
 * @since 1.0
 */
public class DtoBody extends DictInput.Envelope {

    /**
     * Object mapper.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        DtoBody.MAPPER.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        );
    }

    /**
     * Ctor.
     * @param dto Dto
     * @param <T> Input type
     */
    public <T> DtoBody(final T dto) {
        super(() -> new Body(DtoBody.MAPPER.writeValueAsString(dto)));
    }

    /**
     * Body from response as dto.
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public static class Of extends Body.Of {

        /**
         * Ctor.
         * @param response Response
         */
        public Of(final Dict response) {
            super(response);
        }

        /**
         * Body deserialization to a concrete class.
         * @param cls Class
         * @param <T> Output type
         * @return Dto Dto
         * @checkstyle MethodNameCheck (2 lines)
         */
        public final <T> T as(final Class<T> cls) {
            return new UncheckedScalar<>(
                () -> DtoBody.MAPPER.readValue(this.asString(), cls)
            ).value();
        }
    }
}
