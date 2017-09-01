package com.edassist.validator;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.edassist.exception.BadRequestException;

public class ValidatingHttpRequest extends HttpServletRequestWrapper {

	public ValidatingHttpRequest(HttpServletRequest request) {
		super(request);

		boolean verboten = false;
		CharSequence gt = "<";
		CharSequence pctGT = "%3c";

		try {
			String cleanQuery = Normalizer.normalize(request.getQueryString(), Form.NFC);

			if (cleanQuery.contains(gt) || cleanQuery.contains(pctGT)) {
				verboten = true;
			}
		} catch (Exception e1) {
		}

		if (verboten) {
			throw new BadRequestException("Forbidden character in URL string.");
		}

	}

}
