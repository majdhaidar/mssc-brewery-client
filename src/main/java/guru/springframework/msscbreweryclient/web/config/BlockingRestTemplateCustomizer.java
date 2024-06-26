package guru.springframework.msscbreweryclient.web.config;

import lombok.Setter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Below configuration will save us time in initializing new requests per request
 * with pooling we will be able to use existing initialized requests
 * which will lower time for creating new requests each time
 */
@Component
@ConfigurationProperties(prefix = "resttemp.customizer", ignoreUnknownFields = false)
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    @Setter
    private int maxConnectionPool;
    @Setter
    private int defaultMaxPerRoute;
    @Setter
    private int connectionRequestTimeout;
    @Setter
    private int socketTimeout;

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }

    /**
     * Connection Pooling: By using a PoolingHttpClientConnectionManager, you can manage a pool of HTTP connections, reducing the overhead of creating and closing connections for each request. This can improve performance by reusing existing connections and minimizing the time spent establishing new connections.
     * <p>
     * Timeouts: Setting connection and socket timeouts can prevent your application from waiting indefinitely for a response from the server. This ensures that resources are not tied up unnecessarily and helps to maintain responsiveness.
     * <p>
     * Keep-Alive Strategy: The keep-alive strategy helps in reusing persistent connections to the server, reducing the latency incurred by establishing new connections for subsequent requests. This is particularly beneficial in scenarios where multiple requests are made to the same server within a short period.
     * <p>
     * Asynchronous Execution: Although your current configuration doesn't include asynchronous execution, the httpasyncclient library you added supports asynchronous HTTP requests. This can be advantageous in situations where you need to perform multiple HTTP requests concurrently without blocking the calling thread.
     * <p>
     * Scalability: By optimizing connection management and reducing blocking operations, your application can better handle a larger number of concurrent requests, leading to improved scalability.
     *
     * @return
     */
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

        poolingHttpClientConnectionManager.setMaxTotal(maxConnectionPool);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();

        CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).build();

        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }
}

