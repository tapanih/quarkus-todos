package dev.honkanen

import io.quarkus.logging.Log
import io.quarkus.qute.CheckedTemplate
import io.quarkus.qute.TemplateInstance
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestForm


data class Todo(
    val id: Int,
    var title: String,
    var completed: Boolean
)

data class TodoForm(
    @field:RestForm
    var title: String? = null
)

@CheckedTemplate
object Templates {
    @JvmStatic
    external fun index(todos: List<Todo>): TemplateInstance

    @JvmStatic
    external fun `index$todos`(todos: List<Todo>): TemplateInstance

    @JvmStatic
    external fun `index$todo`(todo: Todo): TemplateInstance

    @JvmStatic
    external fun edit(todo: Todo): TemplateInstance
}

@Path("/todos")
class TodoResource {

    companion object {
        var todos = mutableListOf(
            Todo(
                id = 1,
                title = "Do the dishes",
                completed = false
            ),
            Todo(
                id = 2,
                title = "Do the laundry",
                completed = false
            ),
            Todo(
                id = 3,
                title = "Do the shopping",
                completed = false
            )
        )
    }

    fun List<Todo>.getByIdOrThrow(id: Int): Todo {
        return find { it.id == id } ?: throw NotFoundException()
    }

    @GET
    fun get(): TemplateInstance = Templates.index(todos.sortedBy { it.completed })

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun post(@BeanParam form: TodoForm): TemplateInstance {
        val todo = Todo(
            id = todos.size + 1,
            title = form.title!!,
            completed = false
        )
        todos.add(todo)
        Log.debug("Added todo: $todo")
        return Templates.`index$todos`(todos.sortedBy { it.completed })
    }

    @GET
    @Path("/{id}")
    fun get(@PathParam("id") id: Int): TemplateInstance {
        val todo = todos.getByIdOrThrow(id)
        return Templates.`index$todo`(todo)
    }


    @GET
    @Path("/{id}/edit")
    fun edit(@PathParam("id") id: Int): TemplateInstance {
        val todo = todos.getByIdOrThrow(id)
        return Templates.edit(todo)
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun update(@PathParam("id") id: Int, @BeanParam form: TodoForm): TemplateInstance {
        val todo = todos.getByIdOrThrow(id)
        todo.title = form.title!!
        return Templates.`index$todo`(todo)
    }

    @POST
    @Path("/{id}/mark-complete")
    fun markComplete(@PathParam("id") id: Int): TemplateInstance {
        val todo = todos.getByIdOrThrow(id)
        todo.completed = true
        return Templates.`index$todo`(todo)
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Int): String {
        todos.removeIf { it.id == id }
        return ""
    }

}