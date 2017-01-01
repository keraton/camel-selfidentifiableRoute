package org.apache.camel.example.springboot.livereload.idutils;

import org.apache.camel.Processor;
import org.apache.camel.example.springboot.livereload.annotation.CamelId;
import org.apache.camel.model.BeanDefinition;
import org.apache.camel.model.ProcessDefinition;
import org.apache.camel.model.ProcessorDefinition;

import java.lang.reflect.Field;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by Bowie on 24/12/2016.
 */
class IdExtractor {

    String resolveCamelId(ProcessorDefinition processorDefinition) {
        String camelId = null;

        if (processorDefinition instanceof ProcessDefinition) {

            ProcessDefinition processDefinition = (ProcessDefinition) processorDefinition;

            Class<?> processorClass = getProcessorClass(processDefinition, "processor");
            camelId = resolve(processorClass);


        }

        if (processorDefinition instanceof BeanDefinition) {
            BeanDefinition beanDefinition = (BeanDefinition) processorDefinition;

            Class<?> processorClass = getProcessorClass(beanDefinition, "bean");
            camelId = resolve(processorClass);

        }
        return camelId;
    }

    private String resolve(Class<?> processorClass) {
        String camelId;
        camelId = extractCamelIDFromAnnotation(processorClass);
        if (camelId == null) {
            camelId = processorClass.getSimpleName();
        }
        return camelId;
    }

    private String extractCamelIDFromAnnotation(Class<?> processorClass) {
        String camelId = null;
        Optional<CamelId> optionalCamelId = asList(processorClass.getDeclaredAnnotations()).stream()
                .filter(annotation -> annotation instanceof CamelId)
                .map(annotation -> (CamelId) annotation)
                .findFirst();

        if (optionalCamelId.isPresent()) {
            camelId = optionalCamelId.get().value();
        }
        return camelId;
    }

    private Class<?> getProcessorClass(ProcessorDefinition processorDefinition, String type) {
        try {
            Field f = processorDefinition.getClass().getDeclaredField(type);
            f.setAccessible(true);
            Processor processor = (Processor) f.get(processorDefinition); //IllegalAccessException

            return processor.getClass();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }

    }
}
