package com.buschmais.jqassistant.plugin.jaxrs.test;

import static com.buschmais.jqassistant.core.analysis.api.Result.Status.SUCCESS;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import javax.ws.rs.ext.ContextResolver;

import org.junit.Test;

import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import com.buschmais.jqassistant.plugin.jaxrs.test.set.beans.BookResolver;

/**
 * Test to verify JAX-RS Context Provider concepts.
 *
 * @author Aparna Chaudhary
 */
public class ContextProviderIT extends AbstractJavaPluginIT {

    /**
     * Verifies the concept {@code jaxrs:ContextProvider} for
     * {@link ContextResolver}.
     *
     * @throws java.io.IOException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void contextProvider_Concept() throws Exception {
        scanClasses(BookResolver.class);
        assertThat(applyConcept("jaxrs:ContextProvider").getStatus(), equalTo(SUCCESS));
        store.beginTransaction();
        assertThat("Expected contextProvider", query("MATCH (contextProvider:JaxRS:ContextProvider) RETURN contextProvider").getColumn("contextProvider"),
                hasItem(typeDescriptor(BookResolver.class)));
        store.commitTransaction();
    }

}
