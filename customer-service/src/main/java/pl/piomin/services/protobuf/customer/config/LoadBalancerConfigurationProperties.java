package pl.piomin.services.protobuf.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("spring.cloud.loadbalancer")
public class LoadBalancerConfigurationProperties {

    List<ServiceConfig> instances = new ArrayList<>();

    public List<ServiceConfig> getInstances() {
        return instances;
    }

    public void setInstances(List<ServiceConfig> instances) {
        this.instances = instances;
    }
}
