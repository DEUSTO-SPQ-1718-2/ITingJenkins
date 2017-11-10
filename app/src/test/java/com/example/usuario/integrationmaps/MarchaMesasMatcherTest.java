package com.example.usuario.integrationmaps;

import org.junit.Test;

/**
 * Created by Usuario on 10/11/2017.
 */
import static org.hamcrest.MatcherAssert.assertThat;
public class MarchaMesasMatcherTest {
    @Test
    public void testRegularExpressionMatcher() throws Exception {
        String s ="aaabbbaaaa";
        assertThat(s, MesasMatcher.matchesMesa("5"));
    }
}
