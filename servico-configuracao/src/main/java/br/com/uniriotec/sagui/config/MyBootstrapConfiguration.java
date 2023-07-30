package br.com.uniriotec.sagui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
@Configuration
@Import({DataBaseConfiguration.class})
public class MyBootstrapConfiguration implements Ordered{
    @Override
    public int getOrder() {
        return 0;
    }
}
