package org.example;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

public class FeedbackStubConsumer {

    private final KafkaConsumer<String, String> consumer;

    public FeedbackStubConsumer(String topic) {
        System.out.println("Initializing consumer..");
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
<<<<<<< HEAD:src/main/java/org/example/InferenceResultConsumer.java
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "lark-postprocess");
=======
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "lark-feedback");
>>>>>>> cffd8ee (feat: init):src/main/java/org/example/FeedbackStubConsumer.java
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        System.out.println("Subscribed to topic");
    }

    public String consumeAndProcess() {
        ConsumerRecords<String, String> records = consumer.poll(
            Duration.ofMillis(2)
        );
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf(
                "Consumed: key=%s, value=%s, offset=%s",
                record.key(),
                record.value(),
                record.offset()
            );
            return record.value();
        }
        return null;
    }

    public void close() {
        consumer.close();
    }
}
