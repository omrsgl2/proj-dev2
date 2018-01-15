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
	
@RpcEntity(name="Items", language=Languages.COBOL)
@RpcActions(actions = {
				@Action(action = SHOW.class, path = ""),
				@Action(action = EXECUTE.class, path = "ITEMS", displayName = "Execute", alias = "execute"
						)			})
public class Items {

	@RpcField( originalName = "INNER-RECORD")
	@RpcList(count = 5)
	private List<InnerRecord> innerRecord;

	
	@RpcPart(name = "InnerRecord", originalName = "INNER-RECORD", displayName = "INNERRECORD")
	public static class InnerRecord {

	@RpcNumericField(minimumValue = -9999, maximumValue = 9999, decimalPlaces = 0)	
	@RpcField(length = 2, originalName = "ITEM-NUMBER", legacyType = "Binary Integer")
	private Integer itemNumber;
	
	@RpcField(length = 16, originalName = "ITEM-NAME", legacyType = "Char")
	private String itemName;
	
	@RpcField(length = 28, originalName = "DESCRIPTION", legacyType = "Char")
	private String description;
	}
}

