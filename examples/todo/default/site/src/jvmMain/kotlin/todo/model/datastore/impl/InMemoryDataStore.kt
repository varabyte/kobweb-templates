package todo.model.datastore.impl

import todo.model.TodoItem
import todo.model.datastore.TodoDataStore
import java.util.UUID
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Transient data store implementation that is wiped out every time the server is shutdown.
 */
class InMemoryDataStore : TodoDataStore {
    private val lock = ReentrantLock()
    private val todos = mutableMapOf<String, MutableList<TodoItem>>()

    override suspend fun add(ownerId: String, todo: String) {
        lock.withLock {
            todos.computeIfAbsent(ownerId) { mutableListOf() }.add(TodoItem(UUID.randomUUID().toString(), todo))
        }
    }

    override suspend fun remove(ownerId: String, id: String) {
        lock.withLock { todos[ownerId]?.removeIf { it.id == id } }
    }

    override suspend fun get(ownerId: String): List<TodoItem> {
        return lock.withLock { todos[ownerId] } ?: emptyList()
    }
}
