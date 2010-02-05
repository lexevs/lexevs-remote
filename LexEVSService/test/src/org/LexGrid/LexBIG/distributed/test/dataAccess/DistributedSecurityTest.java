/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.distributed.test.dataAccess;

import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import junit.framework.TestCase;

public class DistributedSecurityTest extends ServiceTestCase {
    String testId = "DistributedSecurityTest";
	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return testId;
	}
	public void testConnectToUnsecuredVocab(){
		LexEVSDistributed lbs = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");		
		}
		
		try {
			lbs.resolveCodingScheme(THES_SCHEME, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving NCI Thesaurus");	
		}
	}

	public void testConnectToSecuredVocabWithToken(){
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);
		
		LexEVSDistributed lbs = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
			lbs.registerSecurityToken(MEDDRA_SCHEME, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");				
		}
		
		try {
			CodingScheme scheme = lbs.resolveCodingScheme(MEDDRA_SCHEME, null);
			assertTrue(scheme != null);
			assertTrue(scheme.getCodingSchemeName().equals(MEDDRA_SCHEME));
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resoloving MedDRA");	
		}
	}

	public void testConnectToSecuredVocabWithoutToken(){
		LexEVSDistributed lbs = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");				
		}
		
		try {
			CodingScheme scheme = lbs.resolveCodingScheme(MEDDRA_SCHEME, null);
			fail("Secure coding cheme was resolved without a token");
		} catch (Exception e) {
			//This is a good thing -- it should  throw an exception when trying
			//to access a secure coding scheme without a token
		}
	}

	public void testMultipleSessions(){
		//Set up two sessions
		LexEVSDistributed lbs = null;
		LexEVSDistributed lbs2 = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
			lbs2 = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");	
		}
		
		//try the first one (unsecured)
		try {
			CodingScheme scheme = lbs.resolveCodingScheme(MEDDRA_SCHEME, null);
			fail("Secure coding cheme was resolved without a token");
		} catch (Exception e) {
			//This is a good thing -- it should  throw an exception when trying
			//to access a secure coding scheme without a token
		}
		
		//try the second one (unsecured)
		try {
			CodingScheme scheme = lbs2.resolveCodingScheme(MEDDRA_SCHEME, null);
			fail("Secure coding cheme was resolved without a token");
		} catch (Exception e) {
			//This is a good thing -- it should  throw an exception when trying
			//to access a secure coding scheme without a token
		}
		
		//try the first one again (secured with token)
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);
		try {
			lbs.registerSecurityToken(MEDDRA_SCHEME, token);
			CodingScheme scheme = lbs.resolveCodingScheme(MEDDRA_SCHEME, null);
			assertTrue(scheme != null);
			assertTrue(scheme.getCodingSchemeName().equals(MEDDRA_SCHEME));
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resoloving MedDRA");	
		}
		
		//try the second one (secured WITHOUT token)
		try {
			CodingScheme scheme = lbs2.resolveCodingScheme(MEDDRA_SCHEME, null);
			fail("Secure coding cheme was resolved without a token");
		} catch (Exception e) {
			//This is a good thing -- it should  throw an exception when trying
			//to access a secure coding scheme without a token
		}	
	}	
	
	
	
}
