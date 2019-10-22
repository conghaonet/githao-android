package com.app2m.githaoa.network.https

import android.os.Build
import android.util.Log

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.ArrayList

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion

/**
 * Enables TLS v1.2 when creating SSLSockets.
 *
 *
 * For some reason, android supports TLS v1.2 from API 16, but enables it by
 * default only from API 20.
 * @author conghao
 * @link https://developer.android.com/reference/javax/net/ssl/SSLSocket.html
 * @see SSLSocketFactory
 */
class Tls12SocketFactory private constructor(private val delegate: SSLSocketFactory) : SSLSocketFactory() {
    companion object {
        @JvmStatic
        private val TLS_V12_ONLY = arrayOf(TlsVersion.TLS_1_2.javaName)
        @JvmStatic
        fun enableTls12OnKitkat(client: OkHttpClient.Builder): OkHttpClient.Builder {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                return client
            }
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            if (sslContext != null) {
                try {
                    sslContext.init(null, null, null)
                    client.sslSocketFactory(Tls12SocketFactory(sslContext.socketFactory), HttpsTrustManager())
                    client.hostnameVerifier(HttpsTrustManager.TrustAllHostnameVerifier())
                    val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(*TLS_V12_ONLY)
                        .build()
                    val specList = ArrayList<ConnectionSpec>()
                    specList.add(connectionSpec)
                    specList.add(ConnectionSpec.COMPATIBLE_TLS)
                    specList.add(ConnectionSpec.CLEARTEXT)

                    client.connectionSpecs(specList)
                } catch (e: KeyManagementException) {
                    Log.e("Tls12SocketFactory", "Error while setting " + TlsVersion.TLS_1_2.javaName, e)
                }
            }
            return client
        }
    }

    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket? {
        return patch(delegate.createSocket(s, host, port, autoClose))
    }

    @Throws(IOException::class)
    override fun createSocket(host: String, port: Int): Socket? {
        return patch(delegate.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket? {
        return patch(delegate.createSocket(host, port, localHost, localPort))
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket? {
        return patch(delegate.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket? {
        return patch(delegate.createSocket(address, port, localAddress, localPort))
    }

    private fun patch(s: Socket): Socket {
        if (s is SSLSocket) {
            s.enabledProtocols = TLS_V12_ONLY
        }
        return s
    }
}