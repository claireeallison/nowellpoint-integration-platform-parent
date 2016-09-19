package com.nowellpoint.api.model.codec;

import com.nowellpoint.api.model.document.IsoCountry;
import com.nowellpoint.mongodb.document.AbstractCodec;

public class IsoCountryCodec extends AbstractCodec<IsoCountry> {
	
	public IsoCountryCodec() {
		super(IsoCountry.class);
	}
}