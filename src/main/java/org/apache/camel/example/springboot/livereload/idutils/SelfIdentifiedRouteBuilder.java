package org.apache.camel.example.springboot.livereload.idutils;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

import java.util.List;

/**
 * Created by Bowie on 23/12/2016.
 */
public abstract class SelfIdentifiedRouteBuilder extends RouteBuilder {

    private IdExtractor idExtractor = new IdExtractor();

    @Override
    protected void checkInitialized() throws Exception {
        // Initialized the camel route
        super.checkInitialized();

        getRouteCollection().getRoutes().stream()
                .map(routeDefinition -> routeDefinition.getOutputs())
                .forEach(this::setIdToProcessor);

        getRouteCollection().getOnExceptions().stream()
                .map(routeDefinition -> routeDefinition.getOutputs())
                .forEach(this::setIdToProcessor);

    }

    private void setIdToProcessor(List<ProcessorDefinition<?>> outputs) {
        for (ProcessorDefinition processorDefinition : outputs) {
            if (processorDefinition.getId() == null) {
                processorDefinition.setId(idExtractor.resolveCamelId(processorDefinition));
            }
            setIdToProcessor(processorDefinition.getOutputs());
        }
    }



}
