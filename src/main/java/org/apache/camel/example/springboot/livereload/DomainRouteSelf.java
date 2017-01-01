package org.apache.camel.example.springboot.livereload;

import org.apache.camel.example.springboot.livereload.idutils.SelfIdentifiedRouteBuilder;
import org.apache.camel.example.springboot.livereload.process.NoSelfExplainedProcessor;
import org.apache.camel.example.springboot.livereload.process.SelfExplainedProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;

/**
 * Created by Bowie on 18/12/2016.
 */
@Component
public class DomainRouteSelf extends SelfIdentifiedRouteBuilder {

    private final NoSelfExplainedProcessor noSelfExplainedProcessor;

    @Autowired
    public DomainRouteSelf(NoSelfExplainedProcessor noSelfExplainedProcessor) {
        super();
        this.noSelfExplainedProcessor = noSelfExplainedProcessor;
    }


    @Override
    public void configure() throws Exception {

        from("direct:domain")
                .transform().constant("Change me")
                .process(noSelfExplainedProcessor)
                .process(noSelfExplainedProcessor)
                .bean(noSelfExplainedProcessor)

                .choice()
                    .when(exchange -> TRUE.equals(exchange.getProperty("CALL_ONCE")))
                        .process(noSelfExplainedProcessor)

                    .otherwise()
                        .process(noSelfExplainedProcessor)
                        .process(noSelfExplainedProcessor)
                    .end()

                .end()

                .process(new SelfExplainedProcessor())

        .routeId("Add Toto ID");
    }


}
