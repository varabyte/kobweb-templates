package todo.mongodb.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.Body
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.text
import kotlinx.serialization.json.Json
import todo.mongodb.model.TodoStore

@Api
suspend fun listTodos(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.GET) return
    val ownerId = ctx.req.params["owner"] ?: return

    val todos = ctx.data.getValue<TodoStore>()
    ctx.res.body = Body.text(Json.encodeToString(todos[ownerId]))
}
