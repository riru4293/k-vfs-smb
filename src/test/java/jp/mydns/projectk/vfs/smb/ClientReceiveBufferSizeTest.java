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
 * Test of class ClientReceiveBufferSize.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class ClientReceiveBufferSizeTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new ClientReceiveBufferSize(Json.createValue(-1)).getValue()).isEqualTo(Json.createValue(-1));
        assertThat(new ClientReceiveBufferSize(Json.createValue(9)).getValue()).isEqualTo(Json.createValue(9));

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new ClientReceiveBufferSize(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be int.", "smb:client.receiveBufferSize");

    }

    /**
     * Test constructor. If argument is valid {@code int}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_int() {

        assertThat(new ClientReceiveBufferSize(Integer.MIN_VALUE).getValue()).isEqualTo(Json.createValue(Integer.MIN_VALUE));
        assertThat(new ClientReceiveBufferSize(Integer.MAX_VALUE).getValue()).isEqualTo(Json.createValue(Integer.MAX_VALUE));

    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        FileSystemOptions fsOpts = new FileSystemOptions();

        var instance = new ClientReceiveBufferSize.Resolver().newInstance(Json.createValue(999));

        instance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getReceiveBufferSize()).isEqualTo(999);

        var anotherInstance = new ClientReceiveBufferSize.Resolver().newInstance(Json.createValue(0));

        anotherInstance.apply(fsOpts);
        assertThat(toPropertyConfiguration(fsOpts).getReceiveBufferSize()).isZero();

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        ClientReceiveBufferSize base = new ClientReceiveBufferSize(100);
        ClientReceiveBufferSize same = new ClientReceiveBufferSize(100);
        ClientReceiveBufferSize another = new ClientReceiveBufferSize(101);

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

        JsonValue expectValue = Json.createValue(Integer.MAX_VALUE);

        var instance = new ClientReceiveBufferSize.Resolver().newInstance(Json.createValue(Integer.MAX_VALUE));

        assertThat(instance).returns("smb:client.receiveBufferSize", FileOption::getName)
                .returns(expectValue, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        String expect = Json.createObjectBuilder().add("smb:client.receiveBufferSize", 100).build().toString();

        var instance = new ClientReceiveBufferSize(100);

        var result = instance.toString();

        assertThat(result).isEqualTo(expect);

    }
}
