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
import jp.mydns.projectk.vfs.FileOption;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * Test of class NetbiosLocalAddress.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class NetbiosLocalAddressTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        JsonValue src = Json.createValue("localhost");
        JsonValue expext = Json.createValue("localhost");

        assertThat(new NetbiosLocalAddress(src).getValue()).isEqualTo(expext);

    }

    /**
     * Test constructor. If argument is valid {@code String}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_String() {

        JsonValue expect = Json.createValue("127.0.0.1");

        assertThat(new NetbiosLocalAddress("127.0.0.1").getValue()).isEqualTo(expect);

    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        FileSystemOptions fsOpts = new FileSystemOptions();

        JsonValue src = Json.createValue("localhost");
        InetAddress expect = InetAddress.getByName("localhost");

        var trueInstance = new NetbiosLocalAddress.Resolver().newInstance(src);

        trueInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getNetbiosLocalAddress()).isEqualTo(expect);

        JsonValue anotherSrc = Json.createValue("127.0.0.1");
        InetAddress anotherExpect = InetAddress.getByName("127.0.0.1");

        var falseInstance = new NetbiosLocalAddress.Resolver().newInstance(anotherSrc);

        falseInstance.apply(fsOpts);

        assertThat(toPropertyConfiguration(fsOpts).getNetbiosLocalAddress()).isEqualTo(anotherExpect);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        NetbiosLocalAddress base = new NetbiosLocalAddress("127.0.0.1");
        NetbiosLocalAddress same = new NetbiosLocalAddress("127.0.0.1");
        NetbiosLocalAddress another = new NetbiosLocalAddress("localhost");

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

        JsonValue expectValue = Json.createValue("127.0.0.1");

        var instance = new NetbiosLocalAddress.Resolver().newInstance(Json.createValue("127.0.0.1"));

        assertThat(instance).returns("smb:netbios.localAddress", FileOption::getName)
                .returns(expectValue, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        var instance = new NetbiosLocalAddress("127.0.0.1");

        var result = instance.toString();

        assertThat(result).isEqualTo("{\"smb:netbios.localAddress\":\"127.0.0.1\"}");

    }
}
