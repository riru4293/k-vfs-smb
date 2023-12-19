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
import org.junit.jupiter.api.Test;

/**
 * Test of class ClientDefaultUserName.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class ClientDefaultUserNameTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        JsonValue src = Json.createValue("text");
        JsonValue expext = Json.createValue("text");

        assertThat(new ClientDefaultUserName(src).getValue()).isEqualTo(expext);

    }

    /**
     * Test constructor. If argument is valid {@code String}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_String() {

        JsonValue expect = Json.createValue("text");

        assertThat(new ClientDefaultUserName("text").getValue()).isEqualTo(expect);

    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        FileSystemOptions fsOpts = new FileSystemOptions();

        JsonValue src = Json.createValue("text");

        var trueInstance = new ClientDefaultUserName.Resolver().newInstance(src);

        trueInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getDefaultUsername()).isEqualTo("text");

        JsonValue anotherSrc = Json.createValue("anotherText");

        var falseInstance = new ClientDefaultUserName.Resolver().newInstance(anotherSrc);

        falseInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getDefaultUsername()).isEqualTo("anotherText");

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        ClientDefaultUserName base = new ClientDefaultUserName("text");
        ClientDefaultUserName same = new ClientDefaultUserName("text");
        ClientDefaultUserName another = new ClientDefaultUserName("anotherText");

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

        JsonValue expectValue = Json.createValue("text");

        var instance = new ClientDefaultUserName.Resolver().newInstance(Json.createValue("text"));

        assertThat(instance).returns("smb:client.defaultUserName", FileOption::getName)
                .returns(expectValue, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        var instance = new ClientDefaultUserName("text");

        var result = instance.toString();

        assertThat(result).isEqualTo("{\"smb:client.defaultUserName\":\"text\"}");

    }
}
