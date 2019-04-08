package com.wsss.frame.aviator.function;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.utils.TypeUtils;

public class NumberConverFunction extends AbstractFunction  {

	@Override
	public String getName() {
		return "num";
	}

	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
		String num = (String)arg1.getValue(env);
		try {
			if(num.contains(".")) {
				BigDecimal number = new BigDecimal(num);
				return new AviatorDecimal(number);
			}else {
				return new AviatorBigInt(new BigInteger(num));
			}
		} catch(Exception e) {
			return AviatorNil.NIL;
		}
	}

	

}
