package todo.model.datastore

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import todo.model.datastore.impl.InMemoryDataStore
import todo.model.TodoItem
import todo.model.datastore.impl.MongoDbDataStore

interface TodoDataStore {
    suspend fun add(ownerId: String, todo: String)
    suspend fun remove(ownerId: String, id: String)
    suspend operator fun get(ownerId: String): List<TodoItem>
}

enum class TodoStoreImpl(internal val init: () -> TodoDataStore) {
    InMemory({ InMemoryDataStore() }),
    MongoDb({
        val database = MongoClient.create("mongodb://localhost:27017")
            .getDatabase("kobweb-todo-example")

        MongoDbDataStore(database)
    })
}
@InitApi
fun initTodoStore(ctx: InitApiContext) {
    // The following property should be set in the build script
    val datastoreImplProperty: String? = System.getProperty("todo.datastore.impl")
    val store = if (datastoreImplProperty != null) {
        TodoStoreImpl.valueOf(datastoreImplProperty)
    } else {
        ctx.logger.warn("System property \"todo.datastore.impl\" was not set; falling back to in-memory implementation.")
        TodoStoreImpl.InMemory
    }

    ctx.data.add(store.init())
    ctx.logger.info("Created data store (type = ${store.name})")
}
