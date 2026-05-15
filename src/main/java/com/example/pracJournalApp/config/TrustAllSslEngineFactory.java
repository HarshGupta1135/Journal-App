package com.example.pracJournalApp.config;

import org.apache.kafka.common.security.auth.SslEngineFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Map;

public class TrustAllSslEngineFactory implements SslEngineFactory {

    private SSLContext sslContext;

    @Override
    public void configure(Map<String, ?> configs) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            this.sslContext = SSLContext.getInstance("TLS");
            this.sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize TrustAllSslEngineFactory", e);
        }
    }

    @Override
    public SSLEngine createClientSslEngine(String peerHost, int peerPort, String endpointIdentification) {
        SSLEngine engine = sslContext.createSSLEngine(peerHost, peerPort);
        engine.setUseClientMode(true);
        return engine;
    }

    @Override
    public SSLEngine createServerSslEngine(String peerHost, int peerPort) {
        throw new UnsupportedOperationException("Server mode not supported");
    }

    @Override
    public boolean shouldBeRebuilt(Map<String, Object> nextConfigs) {
        return false;
    }

    @Override
    public java.util.Set<String> reconfigurableConfigs() {
        return java.util.Collections.emptySet();
    }

    @Override
    public java.security.KeyStore keystore() {
        return null;
    }

    @Override
    public java.security.KeyStore truststore() {
        return null;
    }

    @Override
    public void close() {}
}
