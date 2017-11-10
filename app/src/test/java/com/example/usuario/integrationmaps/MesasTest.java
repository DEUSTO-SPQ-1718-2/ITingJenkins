package com.example.usuario.integrationmaps;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

/**
 * Created by Usuario on 10/11/2017.
 */

public class MesasTest {

    //Matchers genericos para Hamcrest

    /*

    allOf - matches if all matchers match (short circuits)
    anyOf - matches if any matchers match (short circuits)
    not - matches if the wrapped matcher doesn’t match and vice
    equalTo - test object equality using the equals method
    is - decorator for equalTo to improve readability
    hasToString - test Object.toString
    instanceOf, isCompatibleType - test type
    notNullValue, nullValue - test for null
    sameInstance - test object identity
    hasEntry, hasKey, hasValue - test a map contains an entry, key or value
    hasItem, hasItems - test a collection contains elements
    hasItemInArray - test an array contains an element
    closeTo - test floating point values are close to a given value
    greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
    equalToIgnoringCase - test string equality ignoring case
    equalToIgnoringWhiteSpace - test string equality ignoring differences in runs of whitespace
    containsString, endsWith, startsWith - test string matching
     */
    @Test
    public void listShouldInitiallyBeEmpty() {

        List<Integer> list = Arrays.asList(5, 2, 4);

        // ensure the order is correct
        assertThat(list, contains(5, 2, 4));
        assertThat(list, containsInAnyOrder(2, 4, 5));
        assertThat(list, everyItem(greaterThan(1)));

    }
}
