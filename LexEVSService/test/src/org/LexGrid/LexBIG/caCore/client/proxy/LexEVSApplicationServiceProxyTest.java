/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.LexGrid.LexBIG.caCore.client.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.DataServiceLazyLoadable;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.DataServiceSecurityTokenRequired;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.LexEVSSecurityTokenRequired;
import org.LexGrid.LexBIG.caCore.applicationservice.impl.LexEVSApplicationServiceImpl;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.MockMethodInvocation;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Concept;
import org.LexGrid.concepts.Presentation;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;
import org.LexGrid.relations.AssociationSource;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;

import junit.framework.TestCase;

public class LexEVSApplicationServiceProxyTest extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	private LexEVSApplicationServiceProxy proxy;
	
	@Override
	protected String getTestID() {
		return test_id;	
	}
	
	public void setUp(){
		proxy = new LexEVSApplicationServiceProxy();	
	}
	
	public void testIsDataServiceLazyLoadableString1() throws Exception {
		Annotation[] annotations = CodingScheme.class.getMethod("getMappings", null).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable2() throws Exception {
		Annotation[] annotations = LexEVSDistributed.class.getMethod("getSupportedCodingSchemes", null).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable3() throws Exception {
		Annotation[] annotations = LexEVSService.class.getMethod("query", new Class[]{DetachedCriteria.class}).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable4() throws Exception {
		Annotation[] annotations = LexEVSDataService.class.getMethod("query", new Class[]{DetachedCriteria.class, QueryOptions.class}).getAnnotations();
		assertTrue(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}

}