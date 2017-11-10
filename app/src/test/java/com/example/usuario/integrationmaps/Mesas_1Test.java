package com.example.usuario.integrationmaps;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

/**
 * Created by Usuario on 10/11/2017.
 */

public class Mesas_1Test {
    mesa a;

    @Test
    public void fellowShipOfTheRingShouldContainer7() {
        assertThat("Gandalf", length(is(7)));
    }

    public static Matcher<String> length(Matcher<? super Integer> matcher) {
        return new FeatureMatcher<String, Integer>(matcher, "Un string con longitud", "length") {
            @Override
            protected Integer featureValueOf(String actual) {
                return actual.length();
            }
        };
    }

    @Before
    public void init(){
        a = new mesa("1", "Uno", "3", "0", "", "1");
    }
    @Test
    public void objectHasSummaryProperty () {
        assertThat(a, hasProperty("id"));
        assertThat(a, hasProperty("id", equalTo("1")));
    }
}
