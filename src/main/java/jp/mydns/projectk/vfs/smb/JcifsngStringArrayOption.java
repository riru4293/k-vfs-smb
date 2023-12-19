/*
 * Copyright (c) 2023, Project-K
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package jp.mydns.projectk.vfs.smb;

import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import static jakarta.json.JsonValue.ValueType.ARRAY;
import static jakarta.json.JsonValue.ValueType.STRING;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.joining;
import org.apache.commons.vfs2.FileSystemOptions;

/**
 * JCIFS-NG configuration property for array of {@code String} value.
 * <p>
 * Implementation requirements.
 * <ul>
 * <li>This class is immutable and thread-safe.</li>
 * <li>This class and JSON can be converted bidirectionally.</li>
 * <li>Can reflect this class on the {@link FileSystemOptions}.</li>
 * </ul>
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class JcifsngStringArrayOption extends JcifsngOption {

    protected final List<String> values;

    /**
     * Constructor.
     *
     * @param values option values
     * @throws NullPointerException if {@code values} is {@code null} or if contains {@code null} in {@code values}.
     * @since 1.0.0
     */
    protected JcifsngStringArrayOption(List<String> values) {
        this.values = List.copyOf(Objects.requireNonNull(values));
    }

    /**
     * Constructor.
     *
     * @param values option values
     * @throws NullPointerException if {@code values} is {@code null}
     * @throws IllegalArgumentException if {@code values} type is not {@code JsonArray} or if element type of
     * {@code values} is not {@code JsonString}.
     * @since 1.0.0
     */
    protected JcifsngStringArrayOption(JsonValue values) {

        if (values.getValueType() == ARRAY) {

            boolean allIsString = values.asJsonArray().stream().map(JsonValue::getValueType).allMatch(STRING::equals);

            if (!allIsString) {
                throw new IllegalArgumentException("Array elements must be JSON string.");
            }

            this.values = values.asJsonArray().stream().map(JsonString.class::cast).map(JsonString::getString).toList();

        } else {

            throw new IllegalArgumentException("Must be JSON array.");

        }

    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    protected String getValueAsText() {
        return String.valueOf(values.stream().collect(joining(",")));
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public JsonValue getValue() {
        return Json.createArrayBuilder(values).build();
    }
}
