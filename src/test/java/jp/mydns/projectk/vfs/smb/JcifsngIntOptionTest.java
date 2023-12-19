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
import jakarta.json.JsonValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;

/**
 * Test of class JcifsngIntOption.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class JcifsngIntOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThatCode(() -> new Impl(Json.createValue(0))).doesNotThrowAnyException();

    }

    /**
     * Test constructor. If argument is invalid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidArgument() {

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.NULL))
                .withMessage("Must be convertible to type int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.TRUE))
                .withMessage("Must be convertible to type int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.FALSE))
                .withMessage("Must be convertible to type int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.EMPTY_JSON_ARRAY))
                .withMessage("Must be convertible to type int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.EMPTY_JSON_OBJECT))
                .withMessage("Must be convertible to type int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(Json.createValue("")))
                .withMessage("Must be convertible to type int.");

    }

    /**
     * Test constructor. If argument too large as {@code int}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_TooLarge() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Impl(Json.createValue(Long.valueOf(Integer.MAX_VALUE) + 1)))
                .withMessage("Must be convertible to type int.");

    }

    /**
     * Test constructor. If argument is valid {@code int}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_int() {

        assertThatCode(() -> new Impl(0)).doesNotThrowAnyException();

    }

    /**
     * Test of getValue method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValue() {

        JsonValue expect = Json.createValue(100);

        var instance = new Impl(100);

        assertThat(instance.getValue()).isEqualTo(expect);

    }

    /**
     * Test of getValueAsText method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValueAsText() {

        var trueInstance = new Impl(100);

        assertThat(trueInstance.getValueAsText()).isEqualTo("100");

    }

    class Impl extends JcifsngIntOption {

        public Impl(int value) {
            super(value);
        }

        public Impl(JsonValue value) {
            super(value);
        }

    }

}
