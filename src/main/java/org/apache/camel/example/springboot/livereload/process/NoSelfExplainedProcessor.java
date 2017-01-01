package org.apache.camel.example.springboot.livereload.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.example.springboot.livereload.annotation.CamelId;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by Bowie on 18/12/2016.
 */
@Component
@CamelId("ExplainedProcessor")
public class NoSelfExplainedProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String bodyContent = exchange.getIn().getBody(String.class);

        exchange.getOut().setBody(bodyContent + " toto");
    }
}
