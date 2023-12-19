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
 * Test of class JcifsngBooleanOption.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class JcifsngBooleanOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThatCode(() -> new Impl(JsonValue.TRUE)).doesNotThrowAnyException();
        
        assertThatCode(() -> new Impl(JsonValue.FALSE)).doesNotThrowAnyException();
        
    }

    /**
     * Test constructor. If argument is invalid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidArgument() {
        
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.NULL))
                .withMessage("Must be JSON boolean.");
        
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.EMPTY_JSON_ARRAY))
                .withMessage("Must be JSON boolean.");
        
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(JsonValue.EMPTY_JSON_OBJECT))
                .withMessage("Must be JSON boolean.");
        
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(Json.createValue(0)))
                .withMessage("Must be JSON boolean.");
        
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(Json.createValue("")))
                .withMessage("Must be JSON boolean.");
        
    }

    /**
     * Test constructor. If argument is valid {@code boolean}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_boolean() {
        
        assertThatCode(() -> new Impl(true)).doesNotThrowAnyException();
        
        assertThatCode(() -> new Impl(false)).doesNotThrowAnyException();
        
    }

    /**
     * Test of getValue method. If true.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValue_true() {
        
        JsonValue expect = JsonValue.TRUE;
        
        var instance = new Impl(true);
        
        assertThat(instance.getValue()).isEqualTo(expect);
        
    }

    /**
     * Test of getValue method. if false.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValue_false() {
        
        JsonValue expect = JsonValue.FALSE;
        
        var instance = new Impl(false);
        
        assertThat(instance.getValue()).isEqualTo(expect);
        
    }

    /**
     * Test of getValueAsText method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValueAsText() {
        
        var trueInstance = new Impl(true);
        var falseInstance = new Impl(false);
        
        assertThat(trueInstance.getValueAsText()).isEqualTo("true");
        
        assertThat(falseInstance.getValueAsText()).isEqualTo("false");
        
    }

    class Impl extends JcifsngBooleanOption {

        public Impl(boolean value) {
            super(value);
        }

        public Impl(JsonValue value) {
            super(value);
        }

    }

}
