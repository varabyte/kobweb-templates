package todo.model.datastore.impl

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.serialization.Serializable
import opensavvy.ktmongo.bson.types.ObjectId
import opensavvy.ktmongo.coroutines.asKtMongo
import todo.model.TodoItem
import todo.model.datastore.TodoDataStore

@Serializable
data class TodoItemDto(
    @Suppress("PropertyName") // it's the standard naming in MongoDB
    val _id: ObjectId,
    val ownerId: String,
    val text: String,
)

/**
 * Transient data store implementation that is wiped out every time the server is shutdown.
 */
class MongoDbDataStore(database: MongoDatabase) : TodoDataStore {
    private val collection = database.getCollection<TodoItemDto>("todos").asKtMongo()

    override suspend fun add(ownerId: String, todo: String) {
        val id = collection.newId()

        collection.insertOne(
            TodoItemDto(
                _id = id,
                ownerId = ownerId,
                text = todo,
            )
        )
    }

    override suspend fun remove(ownerId: String, id: String) {
        collection.deleteOne {
            TodoItemDto::_id eq ObjectId(id)
            TodoItemDto::ownerId eq ownerId
        }
    }

    override suspend operator fun get(ownerId: String): List<TodoItem> =
        collection.find {
            TodoItemDto::ownerId eq ownerId
        }.toList()
            .map { TodoItem(it._id.hex, it.text) }
}
