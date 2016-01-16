package com.buschmais.jqassistant.plugin.jaxrs.test;

import static com.buschmais.jqassistant.core.analysis.api.Result.Status.FAILURE;
import static com.buschmais.jqassistant.core.analysis.api.Result.Status.SUCCESS;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.ws.rs.*;

import org.junit.Test;

import com.buschmais.jqassistant.core.analysis.api.AnalysisException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

/**
 * Test to verify JAX-RS Resource method designator concepts.
 * 
 * @author Aparna Chaudhary
 */
public class RequestMethodDesignatorIT extends AbstractJavaPluginIT {

    /**
     * Verifies the concept {@code jaxrs:RequestMethodDesignator}.
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void requestMethodDesignatorConcept() throws Exception {
        scanClasses(GET.class, PUT.class, POST.class, DELETE.class, HEAD.class, OPTIONS.class);
        assertThat(applyConcept("jaxrs:RequestMethodDesignator").getStatus(), equalTo(SUCCESS));
        store.beginTransaction();
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(GET.class)));
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(PUT.class)));
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(POST.class)));
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(DELETE.class)));
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(HEAD.class)));
        assertThat("Expected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"),
                hasItem(typeDescriptor(OPTIONS.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept {@code jaxrs:RequestMethodDesignator} is not applied
     * to invalid annotations.
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void testInvalid_RequestMethodDesignator_Concept() throws Exception {
        scanClasses(Test.class);
        assertThat(applyConcept("jaxrs:RequestMethodDesignator").getStatus(), equalTo(FAILURE));
        store.beginTransaction();
        assertThat("Unexpected RequestMethodDesignator",
                query("MATCH (methodDesignator:JaxRS:RequestMethodDesignator) RETURN methodDesignator").getColumn("methodDesignator"), nullValue());
        store.commitTransaction();
    }

}
