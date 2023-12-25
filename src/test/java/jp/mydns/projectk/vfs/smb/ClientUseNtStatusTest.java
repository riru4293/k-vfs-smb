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
import jp.mydns.projectk.vfs.FileOption;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;

/**
 * Test of class ClientUseNtStatus.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class ClientUseNtStatusTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new ClientUseNtStatus(JsonValue.TRUE).getValue()).isEqualTo(JsonValue.TRUE);
        assertThat(new ClientUseNtStatus(JsonValue.FALSE).getValue()).isEqualTo(JsonValue.FALSE);

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new ClientUseNtStatus(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be boolean.", "smb:client.useNtStatus");

    }

    /**
     * Test constructor. If argument is valid {@code boolean}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_boolean() {

        assertThat(new ClientUseNtStatus(true).getValue()).isEqualTo(JsonValue.TRUE);
        assertThat(new ClientUseNtStatus(false).getValue()).isEqualTo(JsonValue.FALSE);
    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        FileSystemOptions fsOpts = new FileSystemOptions();

        var trueInstance = new ClientUseNtStatus.Resolver().newInstance(JsonValue.TRUE);

        trueInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts)).extracting("useNtStatus").isEqualTo(true);

        var falseInstance = new ClientUseNtStatus.Resolver().newInstance(JsonValue.FALSE);

        falseInstance.apply(fsOpts);
        assertThat(toPropertyConfiguration(fsOpts)).extracting("useNtStatus").isEqualTo(false);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        ClientUseNtStatus base = new ClientUseNtStatus(true);
        ClientUseNtStatus same = new ClientUseNtStatus(true);
        ClientUseNtStatus another = new ClientUseNtStatus(false);

        assertThat(base).hasSameHashCodeAs(same).isEqualTo(same)
                .doesNotHaveSameHashCodeAs(another).isNotEqualTo(another);

    }

    /**
     * Test of newInstance method.
     *
     * @since 1.0.0
     */
    @Test
    void testNewInstance() {

        var instance = new ClientUseNtStatus.Resolver().newInstance(JsonValue.FALSE);

        assertThat(instance).returns("smb:client.useNtStatus", FileOption::getName)
                .returns(JsonValue.FALSE, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        String expect = Json.createObjectBuilder().add("smb:client.useNtStatus", JsonValue.TRUE).build().toString();

        var instance = new ClientUseNtStatus(true);

        var result = instance.toString();

        assertThat(result).isEqualTo(expect);

    }
}
