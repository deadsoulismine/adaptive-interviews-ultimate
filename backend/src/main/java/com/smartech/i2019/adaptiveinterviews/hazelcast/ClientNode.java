package com.smartech.i2019.adaptiveinterviews.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SSLConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.hazelcast.client.properties.ClientProperty.HAZELCAST_CLOUD_DISCOVERY_TOKEN;
import static com.hazelcast.client.properties.ClientProperty.STATISTICS_ENABLED;

@Slf4j
@Configuration
public class ClientNode {

    @Bean
    public HazelcastInstance runHazelcastClient() throws Exception {
        ClassLoader classLoader = ClientNode.class.getClassLoader();

        Properties props = new Properties();
        props.setProperty("javax.net.ssl.keyStore", classLoader.getResource("client.keystore").toURI().getPath());
        props.setProperty("javax.net.ssl.keyStorePassword", "${remote.cluster.password}");
        props.setProperty("javax.net.ssl.trustStore", classLoader.getResource("client.truststore").toURI().getPath());
        props.setProperty("javax.net.ssl.trustStorePassword", "${remote.cluster.password}");

        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().setSSLConfig(new SSLConfig().setEnabled(true).setProperties(props));
        config.setProperty(STATISTICS_ENABLED.getName(), "true");
        config.setProperty(HAZELCAST_CLOUD_DISCOVERY_TOKEN.getName(), "${remote.cluster.discoverytoken}");
        config.setClusterName("${remote.cluster.name}");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        log.info("Connection to remote cluster of hazelcast - successful!");

        client.getConfig().addMapConfig(new MapConfig("user_cache").setTimeToLiveSeconds(60));
        client.getConfig().addMapConfig(new MapConfig("employee_list_cache").setTimeToLiveSeconds(3600));

        return client;
    }

}
