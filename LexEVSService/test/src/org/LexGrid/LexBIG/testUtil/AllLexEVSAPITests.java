/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.testUtil;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.LexGrid.LexBIG.distributed.test.testUtility.ScannedLexBigTests;

/**
 * The Class AllTestsRemoteConfig.
 */
public class AllLexEVSAPITests
{
	/**
	 * Suite.
	 * 
	 * @return the test
	 * 
	 * @throws Exception the exception
	 */
	public static Test suite() throws Exception
	{
		TestSuite mainSuite = new TestSuite("LexEVSAPI JUnit Tests");

		// Only run tests scanned from the LexBIG Local API Test suite.
		mainSuite.addTest(ScannedLexBigTests.suite());

		// These are the old tests.
		//mainSuite.addTest(AllDistributedLexEVSTests.suite());

		// Disable Data Service tests.
		//mainSuite.addTest(AllDataServiceTests.suite());

		return mainSuite;
    }
}