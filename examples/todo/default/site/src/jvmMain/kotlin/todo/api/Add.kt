package todo.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import todo.model.datastore.TodoDataStore

@Api
suspend fun addTodo(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return

    val ownerId = ctx.req.params["owner"]
    val todo = ctx.req.params["todo"]
    if (ownerId == null || todo == null) {
        return
    }

    ctx.data.getValue<TodoDataStore>().add(ownerId, todo)
    ctx.res.status = 200
}