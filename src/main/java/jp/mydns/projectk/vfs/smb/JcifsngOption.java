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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import java.util.Properties;
import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jp.mydns.projectk.vfs.AbstractFileOption;
import jp.mydns.projectk.vfs.provider.jcifsng.SmbFileSystemConfigBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;

/**
 * Configuration property ​​of the JCIFS-NG library.
 * <p>
 * Implementation requirements.
 * <ul>
 * <li>This class is immutable and thread-safe.</li>
 * <li>This class and JSON can be converted bidirectionally.</li>
 * <li>Can reflect this class on the {@link FileSystemOptions}.</li>
 * </ul>
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class JcifsngOption extends AbstractFileOption {

    /**
     * Get the option name of JCIFS-NG.
     * <p>
     * The default implementation returns the value of the {@link JcifsngOption.Name} annotation attached to this class.
     *
     * @return option name of JCIFS-NG. It never {@code null}.
     * @throws IllegalStateException if the option name cannot be resolved
     * @see PropertyConfiguration Name that can be specified
     * @since 1.0.0
     */
    protected String getJcifsngOptionName() {

        JcifsngOption.Name name = this.getClass().getAnnotation(JcifsngOption.Name.class);

        return Optional.ofNullable(name).map(JcifsngOption.Name::value).orElseThrow(
                () -> new IllegalStateException("No found a JCIFS-NG option name."));
    }

    /**
     * Get a string representation option value. This value will be used when applying the option value to the JCIFS-NG
     * library via {@link Properties}.
     *
     * @return option value
     * @since 1.0.0
     */
    protected abstract String getValueAsText();

    /**
     * {@inheritDoc}
     * <p>
     * Applying to {@code opts} via {@link Properties}.
     *
     * @throws NullPointerException if {@code opts} is {@code null}
     * @throws FileSystemException if cannot apply this configuration
     * @see #getJcifsngOptionName()
     * @see #getValueAsText()
     * @since 1.0.0
     */
    @Override
    public void apply(FileSystemOptions opts) throws FileSystemException {

        Properties props = new Properties();
        props.setProperty(getJcifsngOptionName(), getValueAsText());

        final CIFSContext jcifsContext;

        try {
            jcifsContext = new BaseContext(new PropertyConfiguration(props));
        } catch (CIFSException ex) {
            throw new FileSystemException(ex);
        }

        SmbFileSystemConfigBuilder.getInstance().setCIFSContext(opts, jcifsContext);

    }

    /**
     * Indicates the JCIFS-NG option name of the {@link JcifsngOption}.
     *
     * @author riru
     * @version 1.0.0
     * @since 1.0.0
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @Documented
    public @interface Name {

        /**
         * Returns a JCIFS-NG option name of the {@link JcifsngOption}.
         *
         * @return JCIFS-NG option name
         * @since 1.0.0
         */
        String value();
    }
}
