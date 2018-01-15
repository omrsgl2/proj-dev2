package com.mfrpc.openlegacy;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import org.openlegacy.core.annotations.common.*;
import org.openlegacy.core.annotations.rpc.*;
import org.openlegacy.core.FieldType.*;
import org.openlegacy.core.rpc.actions.RpcActions.SHOW;

import org.openlegacy.core.rpc.actions.RpcActions.EXECUTE;


@RpcNavigation(category = "<SIDE MENU NAME TO ACCESS THE PROGRAM>")
	
@RpcEntity(name="Itemde", language=Languages.COBOL)
@RpcActions(actions = {
				@Action(action = SHOW.class, path = ""),
				@Action(action = EXECUTE.class, path = "ITEMDET", displayName = "Execute", alias = "execute"
						)			})
public class Itemde {

	
	@RpcField(originalName = "DFHCOMMAREA", displayName = "Dfhcommarea", legacyType = "struct")
	private Dfhcommarea dfhcommarea;
	
	@RpcPart(name = "Dfhcommarea", originalName = "DFHCOMMAREA", displayName = "Dfhcommarea")
	public static class Dfhcommarea {

	@RpcNumericField(minimumValue = -99999999, maximumValue = 99999999, decimalPlaces = 0)	
	@RpcField(length = 4, originalName = "ITEM-NUM", legacyType = "Binary Integer")
	private Integer itemNum;
	
	@RpcField(originalName = "ITEM-RECORD", displayName = "ITEMRECORD", legacyType = "struct")
	private ItemRecord itemRecord;
	
	@RpcField(originalName = "SHIPPING", displayName = "Shipping", legacyType = "struct")
	private Shipping shipping;
	}
	@RpcPart(name = "ItemRecord", originalName = "ITEM-RECORD", displayName = "ITEMRECORD")
	public static class ItemRecord {

	
	@RpcField(length = 16, originalName = "ITEM-NAME", legacyType = "Char")
	private String itemName;
	
	@RpcField(length = 28, originalName = "DESCRIPTION", legacyType = "Char")
	private String description;
	@RpcNumericField(minimumValue = -9999, maximumValue = 9999, decimalPlaces = 0)	
	@RpcField(length = 2, originalName = "WEIGHT", legacyType = "Binary Integer")
	private Integer weight;
	}
	@RpcPart(name = "Shipping", originalName = "SHIPPING", displayName = "Shipping")
	public static class Shipping {

	
	@RpcField(length = 10, originalName = "SHIPPING-METHOD", legacyType = "Char")
	private String shippingMethod;
	@RpcNumericField(minimumValue = -9999, maximumValue = 9999, decimalPlaces = 0)	
	@RpcField(length = 2, originalName = "DAYS", legacyType = "Binary Integer")
	private Integer days;
	}
}

