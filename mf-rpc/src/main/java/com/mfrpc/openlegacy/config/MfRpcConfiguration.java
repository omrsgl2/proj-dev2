package com.mfrpc.openlegacy.config;

import org.openlegacy.core.EntitiesRegistry;
import org.openlegacy.core.beans.CommonBeanNames;
import org.openlegacy.core.beans.RpcBeanNames;
import org.openlegacy.core.loaders.RegistryLoader;
import org.openlegacy.core.modules.SessionModule;
import org.openlegacy.core.modules.menu.MenuBuilder;
import org.openlegacy.core.rpc.MockRpcConnectionFactory;
import org.openlegacy.core.rpc.RpcConnection;
import org.openlegacy.core.rpc.RpcConnectionFactory;
import org.openlegacy.core.rpc.RpcSession;
import org.openlegacy.impl.support.SessionModules;
import org.openlegacy.providers.mfrpc.utils.OLRpcMfBeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Configuration;
import org.openlegacy.providers.mfrpc.properties.OLCicsProperties;
import org.openlegacy.impl.properties.OLRpcFieldValidationProperties;
import org.openlegacy.impl.rpc.support.binders.converters.ConvertFunctionsHolder;
import org.openlegacy.impl.validator.RpcFieldValidation;
import org.openlegacy.impl.validator.RpcFieldTypeValidation;
import org.openlegacy.impl.rpc.definitions.ConnectorPrimitives;
import org.openlegacy.core.rpc.modules.trail.RpcSessionTrail;
import org.openlegacy.impl.modules.trail.RpcTraiWriter;
import org.openlegacy.impl.properties.OLCommonProperties;

import org.openlegacy.impl.properties.OLDebugProperties;
import org.openlegacy.providers.mfrpc.formatters.*;
import org.openlegacy.providers.mfrpc.transporter.*;
import org.openlegacy.providers.mfrpc.transaction.*;
import org.openlegacy.providers.mfrpc.utils.*;
import org.openlegacy.impl.rpc.support.binders.RpcDirectInputBinder;
import org.openlegacy.impl.rpc.support.binders.RpcDirectOutputBinder;
import org.openlegacy.impl.rpc.support.binders.RpcBindCallback;
import org.openlegacy.impl.rpc.support.binders.RpcDirectBinder;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Arrays;

/**
 * RPC Configuration
 */
@Configuration
public class MfRpcConfiguration {
    private static final String ORCHESTRATED_KEY = "mfRpc";
    private static final String[] packagesToScan = new String[] {"com.mfrpc.openlegacy"};

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_SESSION_SUFFIX)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RpcSession rpcSession(
                @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_CONNECTION_SUFFIX) RpcConnection rpcConnection,
                @Qualifier(ORCHESTRATED_KEY + CommonBeanNames.SESSION_MODULES_SUFFIX) SessionModules sessionModules) {
        return OLRpcMfBeanUtils.rpcSession(rpcConnection, sessionModules);
    }

    @Bean(ORCHESTRATED_KEY + CommonBeanNames.SESSION_MODULES_SUFFIX)
    public SessionModules sessionModules(
                @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_TRAIL_MODULE) SessionModule rpcTrailModule,
                @Qualifier(ORCHESTRATED_KEY + CommonBeanNames.SESSION_REGISTRY_MODULE_SUFFIX) SessionModule sessionRegistryModule,
                @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_MENU_MODULE_SUFFIX) SessionModule rpcMenuModule,
                @Qualifier(RpcBeanNames.RPC_LOGIN_MODULE) SessionModule rpcLoginModule,
                @Qualifier(CommonBeanNames.ROLES_MODULE) SessionModule rolesModule,
                @Qualifier(CommonBeanNames.CACHE_MODULE) SessionModule cacheModule,
                @Qualifier(RpcBeanNames.RPC_TRANSACTION_MODULE) SessionModule rpcTransactionModule) {
        return OLRpcMfBeanUtils.sessionModules(Arrays.asList(
            rpcTrailModule,
            sessionRegistryModule,
            rpcMenuModule,
            rpcLoginModule,
            rolesModule,
            cacheModule,
            rpcTransactionModule));
    }

    @Bean(ORCHESTRATED_KEY + CommonBeanNames.MENU_BUILDER_SUFFIX)
    public MenuBuilder menuBuilder() {
        return OLRpcMfBeanUtils.menuBuilder(ORCHESTRATED_KEY);
    }

    @Bean(ORCHESTRATED_KEY + CommonBeanNames.SESSION_REGISTRY_MODULE_SUFFIX)
    public SessionModule sessionRegistryModule() {
        return OLRpcMfBeanUtils.sessionRegistryModule(ORCHESTRATED_KEY);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_MENU_MODULE_SUFFIX)
    public SessionModule rpcMenuModule() {
        return OLRpcMfBeanUtils.rpcMenuModule(ORCHESTRATED_KEY);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_ENTITIES_REGISTRY_SUFFIX)
    public EntitiesRegistry<?, ?, ?> rpcRegistry(@Qualifier(RpcBeanNames.RPC_REGISTRY_LOADER) RegistryLoader registryLoader) {
        return OLRpcMfBeanUtils.rpcRegistry(Arrays.asList(packagesToScan), registryLoader);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_PROVIDER_PRIMITIVES)
    public ConnectorPrimitives connectorPrimitives() {
         return OLRpcMfBeanUtils.rpcConnectorPrimitives();
    }


    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_MOCK_CONNECTION_FACTORY_SUFFIX)
    public MockRpcConnectionFactory mockRpcConnectionFactory() {
        return OLRpcMfBeanUtils.mockRpcConnectionFactory(ORCHESTRATED_KEY);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_CONNECTION_SUFFIX)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RpcConnection rpcConnection() {
        return OLRpcMfBeanUtils.rpcConnection(ORCHESTRATED_KEY);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_CONNECTION_FACTORY_SUFFIX)
    public RpcConnectionFactory cicsRpcConnectionFactory(OLCicsProperties olCicsProperties) {
        return OLRpcMfBeanUtils.cicsConnectionFactory(ORCHESTRATED_KEY, olCicsProperties);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.CICS_HEADER_UTIL_SUFFIX)
    public CicsHeaderUtil cicsHeaderUtil(OLCicsProperties olCicsProperties) {
        return OLRpcMfBeanUtils.cicsHeaderUtil(ORCHESTRATED_KEY, olCicsProperties);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.TRANSPORTER_SUFFIX)
    public MfRpcHttpTransporter cicsTransporter(
            OLCicsProperties olCicsProperties,
            OLDebugProperties olDebugProperties,
            @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.TRANSPORTER_FACTORY_SUFFIX) MfRpcHttpTransporterFactory transporterFactory,
            @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.CICS_HEADER_UTIL_SUFFIX) CicsHeaderUtil cicsHeaderUtil) {
        return OLRpcMfBeanUtils.cicsTransporter(ORCHESTRATED_KEY, olCicsProperties, olDebugProperties, transporterFactory, cicsHeaderUtil);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.TRANSPORTER_FACTORY_SUFFIX)
    public MfRpcHttpTransporterFactory transporterFactory(OLCicsProperties olCicsProperties,
            @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.CICS_INNER_SERVER_SUFFIX) CicsInnerServer cicsInnerServer) {
        return OLRpcMfBeanUtils.cicsTransporterFactory(ORCHESTRATED_KEY, olCicsProperties, cicsInnerServer);
    }

    @Bean(name = ORCHESTRATED_KEY + RpcBeanNames.CICS_INNER_SERVER_SUFFIX, initMethod = "init", destroyMethod = "destroy")
    public CicsInnerServer cicsInnerServer(
            OLCicsProperties olCicsProperties,
            OLDebugProperties olDebugProperties,
            @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.CICS_HEADER_UTIL_SUFFIX) CicsHeaderUtil cicsHeaderUtil) {
        return OLRpcMfBeanUtils.cicsInnerServer(ORCHESTRATED_KEY, olCicsProperties, olDebugProperties, cicsHeaderUtil);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_INPUT_FORMATTER_SUFFIX)
    public static CicsInputFormatter cicsInputFormatter(
			OLCicsProperties olCicsProperties,
			 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_INPUT_BINDER_SUFFIX)RpcDirectBinder<ByteArrayOutputStream> cicsDirectInputBinder) {
		return OLRpcMfBeanUtils.cicsInputFormatter(ORCHESTRATED_KEY, olCicsProperties, cicsDirectInputBinder);
	}

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_INPUT_BINDER_SUFFIX)
    public static RpcDirectInputBinder<ByteArrayOutputStream> cicsDirectInputBinder(
    		 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_INPUT_BINDER_CALLBACK_SUFFIX)RpcBindCallback<ByteArrayOutputStream> rpcBindCallback,
    		 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_ENTITY_FIELDS_VALIDATIONS)RpcFieldValidation rpcFieldValidation,
    		 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.INPUT_CONVERTORS_HOLDER)ConvertFunctionsHolder functionsHolder) {
		return  OLRpcMfBeanUtils.cicsDirectInputBinder(rpcBindCallback, rpcFieldValidation, functionsHolder);
	}

    @Bean(ORCHESTRATED_KEY+RpcBeanNames.RPC_INPUT_BINDER_CALLBACK_SUFFIX)
    public static CicsBindInputCallback cicsBindInputCallback(
    		 @Qualifier(ORCHESTRATED_KEY+RpcBeanNames.RPC_PROVIDER_PRIMITIVES)ConnectorPrimitives connectorPrimitives,
    		 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.MF_CONVERTER_UTIL_SUFFIX)MFConverterUtil mfConverterUtil) {
		return OLRpcMfBeanUtils.cicsBindInputCallback(connectorPrimitives, mfConverterUtil);
	}

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_OUTPUT_FORMATTER_SUFFIX)
    public CicsOutputFormatter cicsOutputFormatter(
            OLDebugProperties olDebugProperties,
            @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_OUTPUT_BINDER_SUFFIX) RpcDirectBinder<ByteArrayInputStream> cicsDirectOutputBinder) {
        return OLRpcMfBeanUtils.cicsOutputFormatter(cicsDirectOutputBinder);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_OUTPUT_BINDER_SUFFIX)
    public static RpcDirectOutputBinder<ByteArrayInputStream> cicsDirectOutputBinder(
    		@Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_OUTPUT_BINDER_CALLBACK_SUFFIX)RpcBindCallback<ByteArrayInputStream> cicsBindCallback,
			@Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_ENTITY_FIELDS_VALIDATIONS)RpcFieldValidation rpcFieldValidation,
			@Qualifier(ORCHESTRATED_KEY + RpcBeanNames.OUTPUT_CONVERTORS_HOLDER)ConvertFunctionsHolder functionsHolder) {
		return OLRpcMfBeanUtils.cicsDirectOutputBinder(cicsBindCallback, rpcFieldValidation, functionsHolder);
	}

    @Bean(ORCHESTRATED_KEY+RpcBeanNames.RPC_OUTPUT_BINDER_CALLBACK_SUFFIX)
    public static CicsBindOutputCallback cicsBindOutputCallback(
    		 @Qualifier(ORCHESTRATED_KEY+RpcBeanNames.RPC_PROVIDER_PRIMITIVES)ConnectorPrimitives connectorPrimitive,
    		 @Qualifier(ORCHESTRATED_KEY + RpcBeanNames.MF_CONVERTER_UTIL_SUFFIX)MFConverterUtil cicsConvertUtil) {
		return  OLRpcMfBeanUtils.cicsBindOutputCallback(connectorPrimitive, cicsConvertUtil);
	}

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.MF_CONVERTER_UTIL_SUFFIX)
    public MFConverterUtil mfConverterUtil(OLCicsProperties olCicsProperties) {
        return OLRpcMfBeanUtils.mfConverterUtil(ORCHESTRATED_KEY, olCicsProperties);
    }


    @Bean(ORCHESTRATED_KEY + RpcBeanNames.INPUT_CONVERTORS_HOLDER)
    public ConvertFunctionsHolder inputConvertHolder() {
        return OLRpcMfBeanUtils.inputConvertFunctionsHolder(null);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.OUTPUT_CONVERTORS_HOLDER)
    public ConvertFunctionsHolder outputConvertHolder() {
        return OLRpcMfBeanUtils.outputConvertFunctionsHolder(null);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_ENTITY_FIELDS_VALIDATIONS)
    public RpcFieldValidation defaultRpcFieldValidation(List<RpcFieldTypeValidation> rpcFieldTypeValidations, OLRpcFieldValidationProperties olFieldValidationProperties) {
        return OLRpcMfBeanUtils.defaultRpcFieldValidation(ORCHESTRATED_KEY, rpcFieldTypeValidations, olFieldValidationProperties);
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_SESSION_TRAIL)
    public RpcSessionTrail rpcSessionTrail(OLCommonProperties commonProperties,
    		@Qualifier((ORCHESTRATED_KEY + RpcBeanNames.RPC_MOCK_CONNECTION_FACTORY_SUFFIX)) MockRpcConnectionFactory mock) {
    	return OLRpcMfBeanUtils.rpcSessionTrail(ORCHESTRATED_KEY, commonProperties, mock.getEntitiesSnapshots());
    }

    @Bean(ORCHESTRATED_KEY + RpcBeanNames.RPC_TRAIL_MODULE)
	public SessionModule rpcTrailModule(@Qualifier(ORCHESTRATED_KEY + RpcBeanNames.RPC_SESSION_TRAIL) RpcSessionTrail sessionTrail,
	 		RpcTraiWriter rpcTrailUtil) {
		return OLRpcMfBeanUtils.rpcTrailModule(sessionTrail, rpcTrailUtil);
	}
}

