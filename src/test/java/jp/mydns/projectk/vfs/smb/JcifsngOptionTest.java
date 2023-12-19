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

import jakarta.json.JsonValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import org.junit.jupiter.api.Test;

/**
 * Test of class JcifsngOptionTest.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class JcifsngOptionTest {

    /**
     * Test of jcifsngOptionName method.
     *
     * @since 1.0.0
     */
    @Test
    void testJcifsngOptionName() throws Exception {
        var option = new SmbOption();

        String result = option.getJcifsngOptionName();

        assertThat(result).isEqualTo("jcifs.smb.test.option");
    }

    /**
     * Test of jcifsngOptionName method. If no found name.
     *
     * @since 1.0.0
     */
    @Test
    void testJcifsngOptionName_NoFoundName() throws Exception {
        var option = new NoNameSmbOption();

        assertThatIllegalStateException().isThrownBy(() -> option.getJcifsngOptionName())
                .withMessage("No found a JCIFS-NG option name.");
    }

    @JcifsngOption.Name("jcifs.smb.test.option")
    public static class SmbOption extends JcifsngOption {

        @Override
        public String getValueAsText() {
            throw new UnsupportedOperationException();
        }

        @Override
        public JsonValue getValue() {
            throw new UnsupportedOperationException();
        }

    }

    public static class NoNameSmbOption extends JcifsngOption {

        @Override
        public String getValueAsText() {
            throw new UnsupportedOperationException();
        }

        @Override
        public JsonValue getValue() {
            throw new UnsupportedOperationException();
        }

    }
}
