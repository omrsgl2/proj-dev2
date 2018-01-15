package ol.auto.config;

import java.util.Arrays;
import java.util.List;

import org.openlegacy.impl.config.OLCommonBasicConfiguration;
import org.openlegacy.impl.services.ws.ServiceBinder;
import org.openlegacy.impl.utils.OLCommonBeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;


/**
* Orchestration configuration
*/
@Configuration
public class MfRpcWrapperConfiguration extends OLCommonBasicConfiguration {

    private static final String[] servicesPackages = new String[] {"com.mf_rpc_wrapper.openlegacy.services"};

    @Override
    public List<String> getServicesPackages() {
        return Arrays.asList(servicesPackages);
    }

    @Bean
    @Override
    public ServiceBinder serviceBinder() {
        return OLCommonBeanUtils.serviceBinder();
    }

    /* $-a-b-p */
    // IMPORTANT! DO NOT REMOVE OR EDIT PLACEHOLDER
    // Placeholder to add beans dynamically from code

}

