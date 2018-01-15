package ol.test.config;

import org.openlegacy.impl.config.OLCommonBasicConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OLTestAutoConfiguration extends OLCommonBasicConfiguration {
	
	@Override
	public List<String> getServicesPackages() {
		return new ArrayList<>();
	}
	
}
