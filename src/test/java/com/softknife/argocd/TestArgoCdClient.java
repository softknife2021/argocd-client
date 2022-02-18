package com.softknife.argocd;

import com.restbusters.rest.client.HttpMethods;
import com.restbusters.rest.client.RestClientHelper;
import com.restbusters.rest.model.HttpRestRequest;
import com.softknife.argocd.client.ArgoCdClient;
import okhttp3.OkHttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestArgoCdClient {


    private String authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJlYTI5ZjNmYi01OGIyLTQ2NmUtOTdjMS0yNTRmNTcwYjYyNjUiLCJpYXQiOjE2NDM3MDAxMjIsImlzcyI6ImFyZ29jZCIsIm5iZiI6MTY0MzcwMDEyMiwic3ViIjoiYXBpVXNlcjphcGlLZXkifQ.oOTyt_5YsA0D5yKAkvlgRinc7QwDw6jm_LZKkSAcJ_0";
    private ArgoCdClient argoCdClient;
    OkHttpClient okHttpClient = RestClientHelper.getInstance().buildBearerClient(authToken, getHeaders());

    public TestArgoCdClient() throws Exception {
    }

    @BeforeClass(alwaysRun = true)
    private void setUp() throws Exception {
        this.argoCdClient = new ArgoCdClient("https://localhost:8080", authToken);
    }

    private Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        return headers;
    }

    @Test
    private void test1() throws IOException, KeyStoreException {
        HttpRestRequest httpRestRequest = new HttpRestRequest(HttpMethods.GET.getValue(), "https://localhost:8080/api/v1/clusters");
        RestClientHelper.getInstance().executeRequest(okHttpClient, httpRestRequest);
    }

//    private void cert() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keyStore.load(new FileInputStream(new File("myKeyStore.jks")), "mypassword".toCharArray());
//
//        Certificate[] certificates = keyStore.getCertificateChain("myalias");
//        X509Certificate clientCertificate = (X509Certificate) certificates[0];
//        X509Certificate intermediateCertificate = (X509Certificate) certificates[1];
//        PrivateKey privateKey = (PrivateKey) keyStore.getKey("myalias", "mypassword".toCharArray());
//        PublicKey publicKey = clientCertificate.getPublicKey();
//        KeyPair keyPair = new KeyPair(publicKey, privateKey);
//        HeldCertificate heldCertificate = new HeldCertificate(keyPair, clientCertificate);
//
//        HandshakeCertificates.Builder builder =
//                new HandshakeCertificates.Builder()
//                        .heldCertificate(heldCertificate, intermediateCertificate)
//                        .addPlatformTrustedCertificates();
//
//        HandshakeCertificates handshakeCertificates = builder.build();
//
//        OkHttpClient okClient =
//                new OkHttpClient().newBuilder().readTimeout(30, TimeUnit.SECONDS)
//                        .sslSocketFactory(handshakeCertificates.sslSocketFactory(), handshakeCertificates.trustManager())
//                        .build();
//    }



}
