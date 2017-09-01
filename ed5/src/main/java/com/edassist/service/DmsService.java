package com.edassist.service;

import java.util.Collection;

public interface DmsService {

	int saveToDms(String name, int size, byte[] data, String applicationnum, String clientname, int clientid, Collection documentTypeIds) throws Exception;

}
