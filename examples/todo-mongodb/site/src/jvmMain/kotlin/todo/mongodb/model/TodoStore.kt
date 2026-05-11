package todo.mongodb.model

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.serialization.Serializable
import opensavvy.ktmongo.bson.types.ObjectId
import opensavvy.ktmongo.coroutines.asKtMongo

// NOTE: for this to work, it needs a MongoDB instance running locally.
// You can start one with: docker run -d -p '27017:27017' 'mongo:8.0.6'

@InitApi
fun initTodoStore(ctx: InitApiContext) {
    val collection = MongoClient.create("mongodb://localhost:27017")
        .getDatabase("kobweb-todo-example")

    ctx.data.add(TodoStore(collection))
    ctx.logger.info("Initialized MongoDB store")
}

@Serializable
data class TodoItemDto(
    @Suppress("PropertyName") // it's the standard naming in MongoDB
    val _id: ObjectId,
    val ownerId: String,
    val text: String,
)

class TodoStore(
    database: MongoDatabase,
) {
    private val collection = database.getCollection<TodoItemDto>("todos").asKtMongo()

    suspend fun add(ownerId: String, todo: String) {
        val id = collection.newId()

        collection.insertOne(
            TodoItemDto(
                _id = id,
                ownerId = ownerId,
                text = todo,
            )
        )
    }

    suspend fun remove(ownerId: String, id: String) {
        collection.deleteOne {
            TodoItemDto::_id eq ObjectId(id)
            TodoItemDto::ownerId eq ownerId
        }
    }

    suspend operator fun get(ownerId: String): List<TodoItem> =
        collection.find {
            TodoItemDto::ownerId eq ownerId
        }.toList()
            .map { TodoItem(it._id.hex, it.text) }
}
