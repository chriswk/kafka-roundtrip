/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package roundtriptest

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val topicName = "roundtrip.test"
    async {
        RttConsumer.start(topic = topicName)
    }
    RttProducer.sendMessage(topic = topicName, message = "Hello world")
}