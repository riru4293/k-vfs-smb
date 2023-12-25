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
import jcifs.DialectVersion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;

/**
 * Test of class JcifsngDialectVersionOptionTest.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class JcifsngDialectVersionOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThatCode(() -> new Impl(Json.createValue("SMB1"))).doesNotThrowAnyException();
    }

    /**
     * Test constructor. If argument is invalid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidArgument() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Impl(Json.createValue("SMB0")))
                .withMessage("FileOption value of [X] must be either [SMB1, SMB202, SMB210, SMB300, SMB302, SMB311].");
    }

    /**
     * Test constructor. If argument is valid {@code DialectVersion}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_String() {
        assertThatCode(() -> new Impl(DialectVersion.SMB1)).doesNotThrowAnyException();
    }

    /**
     * Test of getValue method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValue() {

        var instance = new Impl(DialectVersion.SMB202);

        assertThat(instance.getValue()).isEqualTo(Json.createValue("SMB202"));

    }

    /**
     * Test of getValueAsText method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValueAsText() {

        var instance = new Impl(DialectVersion.SMB202);

        assertThat(instance.getValueAsText()).isEqualTo("SMB202");

    }

    private class Impl extends JcifsngDialectVersionOption {

        public Impl(JsonValue value) {
            super(value, "X");
        }

        public Impl(DialectVersion value) {
            super(value);
        }

    }

}
