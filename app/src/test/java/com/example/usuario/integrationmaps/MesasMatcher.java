package com.example.usuario.integrationmaps;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Usuario on 10/11/2017.
 */

//Obviamente no da resultado.

public class MesasMatcher extends TypeSafeMatcher<String> {

    private final String texto;

    public MesasMatcher(final String texto){
        this.texto = texto;
    }
    @Override
    protected boolean matchesSafely(String item) {
        return item.matches(texto);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches regular expression=`" + texto + "`");
    }

    public static MesasMatcher matchesMesa(final String regex) {
        return new MesasMatcher(regex);
    }
}
