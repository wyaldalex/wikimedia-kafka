package com.tudux.kafka.producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.tudux.event.WikimediaChangesHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    //no need to add autowire because there is a single parametrized constructor.
    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //To consumer from console: .\kafka-console-consumer.bat --topic wikimedia_recentchange --from-beginning --bootstrap-server localhost:9092
    public void sendMessage() throws InterruptedException {

        String topic = "wikimedia_recentchange";
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        LOGGER.info("Processing events");
        //to read time stream data from wikimedia, we use event source
        EventHandler eventHandler = new WikimediaChangesHandler(topic,kafkaTemplate);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(5);

    }


}
