package roundtriptest

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.Serdes
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Properties

class RttConsumer {

    suspend fun startConsuming(topic: String) {
        println("Consuming")

        KafkaConsumer<String, String>(props()).use {
            it.subscribe(listOf(topic))
            while (true) {
                val records = it.poll(Duration.of(100, ChronoUnit.MILLIS))
                records.forEach {
                    println("At ${Instant.now().toEpochMilli()} - printed ${it.value()}")
                }
            }
        }
    }

    fun props(): Properties {
        val kafkaProps = Properties()
        with(kafkaProps) {
            put("bootstrap.servers", (2009..2013).joinToString(separator = ",") { "dev-confluentkafka$it.sol-osl01.host.finntech.no:7794" })
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Serdes.String().deserializer().javaClass)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Serdes.String().deserializer().javaClass)
            put("group.id", "roundtrip-tester")
        }
        return kafkaProps
    }


    companion object {
        suspend fun start(topic : String) {
            RttConsumer().startConsuming(topic)
        }
    }
}