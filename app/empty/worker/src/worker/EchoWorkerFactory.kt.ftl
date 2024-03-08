package ${package}.worker

import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import com.varabyte.kobweb.worker.createPassThroughSerializer

// TODO: Worker checklist
// - Review https://github.com/varabyte/kobweb#worker
// - Rename this class to a more appropriate worker for your project
// - Choose appropriate input/output generic types for WorkerFactory<I, O>
//   - Consider using Kotlinx serialization for rich I/O types
// - Write strategy implementation logic
// - Update IO serialization override if I/O types changed
// - Delete this checklist!

internal class EchoWorkerFactory : WorkerFactory<String, String> {
    override fun createStrategy(postOutput: OutputDispatcher<String>) = WorkerStrategy<String> { input ->
        postOutput(input) // Add real worker logic here
    }

    override fun createIOSerializer() = createPassThroughSerializer()
}
