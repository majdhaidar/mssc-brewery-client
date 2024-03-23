package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * Just a demo to implementing ni pooling
 *
 */
//@Component
public class NioRestTemplateCustomizer implements RestTemplateCustomizer {

    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor defaultConnectingIOReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().setConnectTimeout(3000).setIoThreadCount(4).setSoTimeout(3000).build());
        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(defaultConnectingIOReactor);
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager).build();
        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }


    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(this.clientHttpRequestFactory());
        } catch (IOReactorException ex) {
        }
    }
}
