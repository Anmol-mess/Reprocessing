package com.springrest.servicerest.Core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.springrest.servicerest.Core.Utility;

import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.builder.support.NoValidationBuilder;

@Component
@Scope(value = "prototype")
public class GenericParser {

    private static final String ST = "ST";
	HapiContext defaultContext = Utility.HAPICONTEXT.get();

    public HapiContext getContext() {

        defaultContext.setValidationRuleBuilder(new NoValidationBuilder());
        defaultContext.setModelClassFactory(null);
        final PipeParser parser = defaultContext.getPipeParser();
        parser.getParserConfiguration().setAllowUnknownVersions(true);
        /*
         * if obx.2 value is missing in obx segment then parser will not parse the
         * message. buildOBXSegment method will replace the correct value later on.
         */

        defaultContext.getParserConfiguration().setDefaultObx2Type(ST);
        defaultContext.getParserConfiguration().setInvalidObx2Type(ST);
        return defaultContext;
    }

    public String getVersion(Message msg) {
        return msg.getVersion();
    }

    public String getName(Message msg) {
        return msg.getName();
    }
    
}
