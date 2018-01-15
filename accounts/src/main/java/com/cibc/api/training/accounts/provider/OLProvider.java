package com.cibc.api.training.accounts.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.mf_rpc_wrapper.openlegacy.MfRpcWrapperApplication;

/**
 * Singleton to use OL SDK
 * <p>
 * Example:
 * <pre>
 * OLProvider.INSTANCE.getApplicationContext().getBean(org.openlegacy.core.rpc.RpcSession.class);
 * </pre>
 */
public enum OLProvider {
	INSTANCE;

	private ApplicationContext applicationContext;

	public void initApplicationContext() {
		this.applicationContext = SpringApplication.run(MfRpcWrapperApplication.class);
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
}
