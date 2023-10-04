/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.cloud.sdk.datamodel.odatav4.referenceservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.sap.cloud.sdk.datamodel.odatav4.core.GetAllRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.referenceservice.namespaces.trippin.Person;
import com.sap.cloud.sdk.datamodel.odatav4.referenceservice.namespaces.trippin.PersonGender;
import com.sap.cloud.sdk.datamodel.odatav4.referenceservice.services.DefaultTrippinService;

public class CollectionQueriesTest
{
    @Test
    public void testGetFilteredWithAny()
    {
        final GetAllRequestBuilder<Person> query =
            new DefaultTrippinService()
                .getAllPeople()
                .filter(Person.TO_FRIENDS.any(Person.FIRST_NAME.equalTo("Angel")));
        final String expectedUnencodedFilter = "Friends/any(a:(a/FirstName eq 'Angel'))";

        assertThat(query.toRequest().getRelativeUri()).hasParameter("$filter", expectedUnencodedFilter);
    }

    @Test
    public void testGetFilteredWithAll()
    {
        final GetAllRequestBuilder<Person> query =
            new DefaultTrippinService()
                .getAllPeople()
                .filter(Person.TO_FRIENDS.all(Person.FIRST_NAME.equalTo("Angel")));
        final String expectedUnencodedFilter = "Friends/all(a:(a/FirstName eq 'Angel'))";

        assertThat(query.toRequest().getRelativeUri()).hasParameter("$filter", expectedUnencodedFilter);
    }

    @Test
    public void testEnumFilter()
    {

        final GetAllRequestBuilder<Person> query =
            new DefaultTrippinService()
                .getAllPeople()
                .filter(Person.TO_FRIENDS.all(Person.GENDER.notEqualTo(PersonGender.MALE)));

        final String expectedUnencodedFilter = "Friends/all(a:(a/Gender ne Trippin.PersonGender'Male'))";

        assertThat(query.toRequest().getRelativeUri()).hasParameter("$filter", expectedUnencodedFilter);
    }
}
