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
import java.util.Objects;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import jcifs.DialectVersion;
import org.apache.commons.vfs2.FileSystemOptions;

/**
 * SMB protocol version.
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
abstract class JcifsngDialectVersionOption extends JcifsngOption {

    private final DialectVersion value;

    /**
     * Constructor.
     *
     * @param value option value
     * @param optionName name of {@code FileOption}. Used in message if occurs {@code IllegalArgumentException}
     * @throws NullPointerException if any argument is {@code null}
     * @throws IllegalArgumentException if {@code value} is not convertible to {@link DialectVersion}
     * @since 1.0.0
     */
    protected JcifsngDialectVersionOption(JsonValue value, String optionName) {

        Objects.requireNonNull(value);
        Objects.requireNonNull(optionName);

        try {

            this.value = DialectVersion.valueOf(JsonString.class.cast(value).getString());

        } catch (ClassCastException | IllegalArgumentException ex) {

            String availables = Stream.of(DialectVersion.values()).map(Enum::name).collect(joining(", "));

            throw new IllegalArgumentException("FileOption value of [%s] must be either [%s]."
                    .formatted(optionName, availables));

        }

    }

    /**
     * Constructor.
     *
     * @param value option value
     * @throws NullPointerException if {@code value} is {@code null}
     * @since 1.0.0
     */
    protected JcifsngDialectVersionOption(DialectVersion value) {

        this.value = Objects.requireNonNull(value);

    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    protected String getValueAsText() {

        return value.name();

    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public JsonValue getValue() {

        return Json.createValue(value.name());

    }

}
