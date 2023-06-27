package com.springrest.servicerest.Core;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;

public class Utility {

    public static final ThreadLocal<HapiContext> HAPICONTEXT = new ThreadLocal<HapiContext>() {
        @Override
        protected HapiContext initialValue() {
            return new DefaultHapiContext();
        }
    };

}