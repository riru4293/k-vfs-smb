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
import jp.mydns.projectk.vfs.FileOption;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * Test of class MinimumVersion.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class MinimumVersionTest extends AbstractOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        var instance = new MinimumVersion(Json.createValue("SMB1"));

        assertThat(instance.getValueAsText()).isEqualTo("SMB1");

    }

    /**
     * Test constructor. If argument is valid {@code DialectVersion}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_DialectVersion() {

        var instance = new MinimumVersion(DialectVersion.SMB1);

        assertThat(instance.getValueAsText()).isEqualTo("SMB1");
    }

    /**
     * Test of apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws Exception {

        var smb1 = new MinimumVersion.Resolver().newInstance(Json.createValue("SMB1"));

        FileSystemOptions fsOpts = new FileSystemOptions();

        smb1.apply(fsOpts);
        assertThat(toPropertyConfiguration(fsOpts).getMinimumVersion()).isEqualTo(DialectVersion.SMB1);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        MinimumVersion base = new MinimumVersion(DialectVersion.SMB1);
        MinimumVersion same = new MinimumVersion(DialectVersion.SMB1);
        MinimumVersion another = new MinimumVersion(DialectVersion.SMB300);

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

        JsonValue expect = Json.createValue("SMB1");

        var instance = new MinimumVersion.Resolver().newInstance(Json.createValue("SMB1"));

        assertThat(instance).returns("smb:minVersion", FileOption::getName)
                .returns(expect, FileOption::getValue);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        String expect = Json.createObjectBuilder().add("smb:minVersion", "SMB1").build().toString();

        var instance = new MinimumVersion(DialectVersion.SMB1);

        var result = instance.toString();

        assertThat(result).isEqualTo(expect);

    }

}
