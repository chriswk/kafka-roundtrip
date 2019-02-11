package roundtriptest

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serdes
import java.time.Instant
import java.util.Properties

class RttProducer {

    companion object {
        fun sendMessage(topic: String, message: String) {
            println("Sending message")
            KafkaProducer<String, String>(props()).use {
                val record = ProducerRecord<String, String>(topic, "at ${Instant.now().toEpochMilli()} $message")
                println(it.send(record).get().timestamp())
            }
        }

        fun props(): Properties {
            val producerProps = Properties()
            with(producerProps) {
                put("bootstrap.servers", (2009..2013).joinToString(separator = ",") { "dev-confluentkafka$it.sol-osl01.host.finntech.no:7794" })
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().javaClass)
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Serdes.String().serializer().javaClass)
                put("client.id", "Roundtriptester")
            }
            return producerProps
        }
    }
}