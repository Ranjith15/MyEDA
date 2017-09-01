package com.edassist.mock;

import org.springframework.web.bind.support.SessionStatus;

public class MockSessionStatus implements SessionStatus {

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setComplete() {
		// TODO Auto-generated method stub

	}

}
