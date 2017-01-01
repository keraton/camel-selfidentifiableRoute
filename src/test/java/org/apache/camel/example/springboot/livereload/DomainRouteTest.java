package org.apache.camel.example.springboot.livereload;

import org.apache.camel.*;
import org.apache.camel.example.springboot.livereload.process.NoSelfExplainedProcessor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;

/**
 * Created by Bowie on 23/12/2016.
 */
public class DomainRouteTest extends CamelTestSupport {

    @Produce(uri = "direct:domain")
    protected ProducerTemplate template;

    @Test
    public void should_print_history () {
        Exchange output = template.send(new DefaultExchange(new DefaultCamelContext()));

        List<MessageHistory> history = (List<MessageHistory>) output.getProperty(Exchange.MESSAGE_HISTORY);
        history.stream().forEach(this::printHistory);
    }

    private void printHistory(MessageHistory messageHistory) {
        NamedNode node = messageHistory.getNode();
        System.out.println(node.getId());
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new DomainRouteSelf(new NoSelfExplainedProcessor());
    }
}