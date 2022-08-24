package com.tudux;

import com.tudux.kafka.producer.WikimediaChangesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Modules are being created with Maven's quick start archetype
@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner
{
    @Autowired
    private WikimediaChangesProducer wikimediaChangesProducer;

    public static void main( String[] args )
    {
        SpringApplication.run(SpringBootProducerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        wikimediaChangesProducer.sendMessage();
    }
}
