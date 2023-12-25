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
import java.net.InetAddress;
import java.util.List;
import jp.mydns.projectk.vfs.FileOption;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;

/**
 * Test of class NetbiosWins.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class NetbiosWinsTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        JsonValue expext = Json.createArrayBuilder().add("localhost").add("127.0.0.1").build();

        assertThat(new NetbiosWins(expext).getValue()).isEqualTo(expext);

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new NetbiosWins(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be list of string.", "smb:netbios.wins");

    }

    /**
     * Test constructor. If argument is valid {@code List<String>}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_List_String() {

        JsonValue expect = Json.createArrayBuilder().add("localhost").add("127.0.0.1").build();

        List<String> value = List.of("localhost", "127.0.0.1");

        assertThat(new NetbiosWins(value).getValue()).isEqualTo(expect);

    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        FileSystemOptions fsOpts = new FileSystemOptions();

        JsonValue value = Json.createArrayBuilder().add("localhost").add("127.0.0.1").build();

        var trueInstance = new NetbiosWins.Resolver().newInstance(value);

        trueInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getWinsServers()).containsExactly(
                InetAddress.getByName("localhost"), InetAddress.getByName("127.0.0.1"));

        JsonValue another = Json.createArrayBuilder().add("localhost").build();

        var falseInstance = new NetbiosWins.Resolver().newInstance(another);

        falseInstance.apply(fsOpts);
        assertThat(toPropertyConfiguration(fsOpts).getWinsServers()).containsExactly(InetAddress.getByName("localhost"));

    }

    /**
     * Test of getValue method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValue() {

        JsonValue expect = Json.createArrayBuilder().add("hello").add("world").build();

        var instance = new NetbiosWins(List.of("hello", "world"));

        assertThat(instance.getValue()).isEqualTo(expect);

    }

    /**
     * Test of getValueAsText method.
     *
     * @since 1.0.0
     */
    @Test
    void testGetValueAsText() {

        var instance = new NetbiosWins(List.of("hello", "world"));

        assertThat(instance.getValueAsText()).isEqualTo("hello,world");

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        NetbiosWins base = new NetbiosWins(List.of("localhost", "127.0.0.1"));
        NetbiosWins same = new NetbiosWins(List.of("localhost", "127.0.0.1"));
        NetbiosWins another = new NetbiosWins(List.of("localhost", "127.0.1.1"));

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

        JsonValue expect = Json.createArrayBuilder().add("localhost").add("127.0.0.1").build();

        var instance = new NetbiosWins.Resolver().newInstance(expect);

        assertThat(instance).returns("smb:netbios.wins", FileOption::getName)
                .returns(expect, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        String expect = Json.createObjectBuilder()
                .add("smb:netbios.wins", Json.createArrayBuilder(List.of("localhost", "127.0.0.1"))).build().toString();

        var instance = new NetbiosWins(List.of("localhost", "127.0.0.1"));

        var result = instance.toString();

        assertThat(result).isEqualTo(expect);

    }
}
