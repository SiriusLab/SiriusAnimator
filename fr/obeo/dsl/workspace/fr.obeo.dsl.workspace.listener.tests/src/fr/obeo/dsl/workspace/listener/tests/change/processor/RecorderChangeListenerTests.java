/*******************************************************************************
 * Copyright (c) 2013 Obeo. All Rights Reserved.
 *
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 *
 *******************************************************************************/
package fr.obeo.dsl.workspace.listener.tests.change.processor;

import fr.obeo.dsl.workspace.listener.change.processor.RecorderChangeListener;
import fr.obeo.dsl.workspace.listener.tests.change.TestChange;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link RecorderChangeListener} class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class RecorderChangeListenerTests {

	/**
	 * Tests {@link RecorderChangeListener#process(fr.obeo.dsl.workspace.listener.change.IChange)}.
	 */
	@Test
	public void processFalse() {
		final TestProcessor processor = new TestProcessor();
		final TestChange change1 = new TestChange();
		final TestChange change2 = new TestChange();
		final TestChange change3 = new TestChange();
		final RecorderChangeListener recorder = new RecorderChangeListener(processor);

		recorder.process(change1);
		recorder.process(change2);
		recorder.process(change3);

		assertEquals(0, processor.getChanges().size());

		recorder.replay();

		assertEquals(3, processor.getChanges().size());
		assertEquals(change1, processor.getChanges().get(0));
		assertEquals(change2, processor.getChanges().get(1));
		assertEquals(change3, processor.getChanges().get(2));
	}

}