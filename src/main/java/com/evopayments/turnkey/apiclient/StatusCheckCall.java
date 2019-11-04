package com.myriadpayments.globalturnkey.apiclient;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.myriadpayments.globalturnkey.apiclient.code.ActionType;
import com.myriadpayments.globalturnkey.apiclient.exception.RequiredParamException;
import com.myriadpayments.globalturnkey.config.ApplicationConfig;

/**
 * Returns the status of an already issued payment transaction, as such it doesn’t actually generate a new transaction
 * 
 * @author erbalazs
 *
 */
public class StatusCheckCall extends ApiCall {

	public StatusCheckCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.STATUS_CHECK;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) throws RequiredParamException {
		// 
	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		tokenParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		tokenParams.put("password", config.getProperty(PASSWORD_PROP_KEY));
		tokenParams.put("action", getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		actionParams.put("token", token);
		actionParams.put("action", getActionType().getCode());
		actionParams.put("txId", inputParams.get("txId"));
		actionParams.put("merchantTxId", inputParams.get("merchantTxId"));

		return actionParams;
	}
}
