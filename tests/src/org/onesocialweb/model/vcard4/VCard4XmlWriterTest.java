/*
 *  Copyright 2010 Vodafone Group Services Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *    
 */
package org.onesocialweb.model.vcard4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.onesocialweb.model.acl.AclAction;
import org.onesocialweb.model.acl.AclFactory;
import org.onesocialweb.model.acl.AclRule;
import org.onesocialweb.model.acl.AclSubject;
import org.onesocialweb.model.acl.DefaultAclFactory;
import org.onesocialweb.model.vcard4.exception.CardinalityException;
import org.onesocialweb.model.vcard4.exception.UnsupportedFieldException;
import org.onesocialweb.xml.writer.VCard4XmlWriter;

public class VCard4XmlWriterTest {

	@Test
	public void entryToXML() throws DocumentException, IOException {
		VCard4Factory profileFactory = new DefaultVCard4Factory();
		AclFactory aclFactory = new DefaultAclFactory();

		List<AclRule> rules = new ArrayList<AclRule>();
		AclRule rule = aclFactory.aclRule();
		rule.addAction(aclFactory.aclAction(AclAction.ACTION_VIEW,
				AclAction.PERMISSION_GRANT));
		rule.addSubject(aclFactory.aclSubject(null, AclSubject.EVERYONE));
		rules.add(rule);

		Profile profile = profileFactory.profile();
		profile.setUserId("test@xmpp.loc");
		Field fnField = profileFactory.fullname("Alice in Wonderland");
		fnField.setAclRules(rules);


		Field sexField = profileFactory.gender(GenderField.Type.FEMALE);
		sexField.setAclRules(rules);


		Field noteField = profileFactory.note("Lost in a land of wonders");
		noteField.setAclRules(rules);

		Field photoField = profileFactory.photo("http://wonderland.lit/alice.jpg");
		photoField.setAclRules(rules);
		
		try {
			profile.addField(fnField);
			profile.addField(sexField);
			profile.addField(photoField);
			profile.addField(noteField);
		} catch (CardinalityException e) {
		} catch (UnsupportedFieldException e) {	
		}
		
		//Very simple stuff...
		VCard4XmlWriter writer=new VCard4XmlWriter();
		System.out.println(writer.toXml(profile));

				
	}
}
