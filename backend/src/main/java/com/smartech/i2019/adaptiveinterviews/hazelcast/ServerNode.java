package com.smartech.i2019.adaptiveinterviews.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerNode {

    //    @Bean
    public void runHazelcastServer() {
        Config cfg = new Config();
        cfg.setClusterName("${local.cluster.name}");

        HazelcastInstance server = Hazelcast.newHazelcastInstance(cfg);
        log.info("Deploy local cluster of hazelcast - successful!");

        server.getConfig().addMapConfig(new MapConfig("user_cache").setTimeToLiveSeconds(60));
        server.getConfig().addMapConfig(new MapConfig("employee_list_cache").setTimeToLiveSeconds(3600));
    }

}
