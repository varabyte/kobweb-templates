package ${package}.worker

import com.varabyte.kobweb.serialization.createIOSerializer
import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// TODO: Worker checklist
// - Review https://kobweb.varabyte.com/docs/concepts/foundation/workers
// - Rename this class to a more appropriate worker for your project
// - Change the Input / Output classes below for your specific needs
// - Write strategy implementation logic ("Add real worker logic here")
// - Delete this checklist when you're done!

@Serializable
data class EchoInput(val message: String)

@Serializable
data class EchoOutput(val message: String)

internal class EchoWorkerFactory : WorkerFactory<EchoInput, EchoOutput> {
    override fun createStrategy(postOutput: OutputDispatcher<EchoOutput>) = WorkerStrategy<EchoInput> { input ->
        postOutput(EchoOutput(input.message)) // Add real worker logic here
    }

    override fun createIOSerializer() = Json.createIOSerializer<EchoInput, EchoOutput>()
}
