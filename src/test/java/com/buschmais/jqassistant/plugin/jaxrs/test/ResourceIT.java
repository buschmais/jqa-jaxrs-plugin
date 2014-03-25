package com.buschmais.jqassistant.plugin.jaxrs.test;

import com.buschmais.jqassistant.core.analysis.api.AnalyzerException;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import com.buschmais.jqassistant.plugin.jaxrs.test.set.beans.MyRestResource;
import org.junit.Test;

import java.io.IOException;

import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test to verify JAX-RS Resource concepts.
 * 
 * @author Aparna Chaudhary
 */
public class ResourceIT extends AbstractPluginIT {

	/**
	 * Verifies the concept {@code jaxrs:Resource}.
	 * 
	 * @throws java.io.IOException
	 *             If the test fails.
	 * @throws AnalyzerException
	 *             If the test fails.
	 * @throws NoSuchMethodException
	 *             If the test fails.
	 */
	@Test
	public void test_Resource_Concept() throws IOException, AnalyzerException, NoSuchMethodException {
		scanClasses(MyRestResource.class);
		applyConcept("jaxrs:Resource");
		store.beginTransaction();
		assertThat("Expected RestResource", query("MATCH (getResource:JaxRS:Resource) RETURN getResource").getColumn("getResource"),
				hasItem(typeDescriptor(MyRestResource.class)));
		store.commitTransaction();
	}

	/**
	 * Verifies the concept {@code jaxrs:Resource} is not applied to invalid Resource classes.
	 * 
	 * @throws java.io.IOException
	 *             If the test fails.
	 * @throws AnalyzerException
	 *             If the test fails.
	 * @throws NoSuchMethodException
	 *             If the test fails.
	 */
	@Test
	public void testInvalid_Resource_Concept() throws IOException, AnalyzerException, NoSuchMethodException {
		scanClasses(ResourceIT.class);
		applyConcept("jaxrs:Resource");
		store.beginTransaction();
		assertThat("Unexpected RestResource", query("MATCH (getResource:JaxRS:Resource) RETURN getResource").getColumn("getResource"),
				nullValue());
		store.commitTransaction();

	}

}
