package org.example;

public class Main {

    public static void main(String[] args) {
        InferenceResultConsumer consumer = new InferenceResultConsumer(
            "inference.result"
        );
        ResponseDeliveryProducer producer = new ResponseDeliveryProducer(
            "response.delivery"
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    System.out.println("Shutting down..");
                    consumer.close();
                    producer.close();
                })
            );

        System.out.printf("Starting Postprocess..");

        try {
            while (true) {
                String processedPrompt = consumer.consumeAndProcess();
                if (processedPrompt != null) {
                    producer.produce("new_responsedel", processedPrompt);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException err) {
            System.err.println("Interrupted: " + err.getMessage());
        }
    }
}
