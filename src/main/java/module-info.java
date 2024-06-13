/*
 * Copyright (c) 2024, Project-K
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

/**
 * Project-K VFS SMB implements.
 *
 * @provides jp.mydns.projectk.vfs.FileOption.Resolver
 * @uses jp.mydns.projectk.vfs.FileOption.Resolver
 *
 * @since 1.0.0
 */
module jp.mydns.projectk.vfs.smb {
    requires jp.mydns.projectk.vfs;
    requires commons.vfs2;
    requires k.vfs.jcifsng.provider;
    requires commons.logging;
    requires org.apache.commons.lang3;
    requires jcifs.ng;
    requires org.slf4j;
    requires org.bouncycastle.provider;
    requires jakarta.json;
    uses jp.mydns.projectk.vfs.FileOption.Resolver;
    provides jp.mydns.projectk.vfs.FileOption.Resolver with
            jp.mydns.projectk.vfs.smb.AllowNtlmFallback.Resolver
          , jp.mydns.projectk.vfs.smb.BufferCacheSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientAllowGuestFallback.Resolver
          , jp.mydns.projectk.vfs.smb.ClientAttributeCacheTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientCapabilities.Resolver
          , jp.mydns.projectk.vfs.smb.ClientConnectionTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDefaultDomain.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDefaultPassword.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDefaultUserName.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDfsConvertToFqdn.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDfsDisabled.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDfsStrictView.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDfsTtl.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDisableIdleTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDisablePlainTextPasswords.Resolver
          , jp.mydns.projectk.vfs.smb.ClientDisableSpnegoIntegrity.Resolver
          , jp.mydns.projectk.vfs.smb.ClientEncryptionEnabled.Resolver
          , jp.mydns.projectk.vfs.smb.ClientEnforceSpnegoIntegrity.Resolver
          , jp.mydns.projectk.vfs.smb.ClientFlags2.Resolver
          , jp.mydns.projectk.vfs.smb.ClientForceExtendedSecurity.Resolver
          , jp.mydns.projectk.vfs.smb.ClientForceUnicode.Resolver
          , jp.mydns.projectk.vfs.smb.ClientGuestPassword.Resolver
          , jp.mydns.projectk.vfs.smb.ClientGuestUserName.Resolver
          , jp.mydns.projectk.vfs.smb.ClientIgnoreCopyToExceptions.Resolver
          , jp.mydns.projectk.vfs.smb.ClientIpcSigningEnforced.Resolver
          , jp.mydns.projectk.vfs.smb.ClientListCount.Resolver
          , jp.mydns.projectk.vfs.smb.ClientListSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientLocalAddress.Resolver
          , jp.mydns.projectk.vfs.smb.ClientLocalPort.Resolver
          , jp.mydns.projectk.vfs.smb.ClientLogonShare.Resolver
          , jp.mydns.projectk.vfs.smb.ClientMaxMpxCount.Resolver
          , jp.mydns.projectk.vfs.smb.ClientMaxRequestRetries.Resolver
          , jp.mydns.projectk.vfs.smb.ClientNativeLanman.Resolver
          , jp.mydns.projectk.vfs.smb.ClientNativeOs.Resolver
          , jp.mydns.projectk.vfs.smb.ClientNotifyBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientPort139Enabled.Resolver
          , jp.mydns.projectk.vfs.smb.ClientReceiveBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientRequireSecureNegotiate.Resolver
          , jp.mydns.projectk.vfs.smb.ClientResponseTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSendBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSendNtlmTargetName.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSessionLimit.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSessionTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSigningEnforced.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSigningPreferred.Resolver
          , jp.mydns.projectk.vfs.smb.ClientSocketTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.ClientStrictResourceLifecycle.Resolver
          , jp.mydns.projectk.vfs.smb.ClientTcpNoDelay.Resolver
          , jp.mydns.projectk.vfs.smb.ClientTransactionBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseBatching.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseExtendedSecurity.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseLargeReadWrite.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseNtSmbs.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseNtStatus.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseSmb2Negotiation.Resolver
          , jp.mydns.projectk.vfs.smb.ClientUseUnicode.Resolver
          , jp.mydns.projectk.vfs.smb.LmCompatibility.Resolver
          , jp.mydns.projectk.vfs.smb.MaximumVersion.Resolver
          , jp.mydns.projectk.vfs.smb.MinimumVersion.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosBroadcastAddress.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosCachePolicy.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosHostname.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosLmhostsFilename.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosLocalAddress.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosLocalPort.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosReceiveBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosRetryCount.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosRetryTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosScope.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosSendBufferSize.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosSocketTimeout.Resolver
          , jp.mydns.projectk.vfs.smb.NetbiosWins.Resolver
          , jp.mydns.projectk.vfs.smb.OemEncoding.Resolver
          , jp.mydns.projectk.vfs.smb.TraceResources.Resolver
          , jp.mydns.projectk.vfs.smb.UseRawNtlm.Resolver;
    exports jp.mydns.projectk.vfs.smb;
}
