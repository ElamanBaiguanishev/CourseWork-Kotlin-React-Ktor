package components

import config.Config
import js.core.get
import js.core.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.input
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import web.html.InputType
import web.http.FormData
import kotlin.js.json


val testInput = FC<Props>("inputText") {

    val queryClient = useQueryClient()
    val myQueryKey = arrayOf("update").unsafeCast<QueryKey>()

    val addMutation = useMutation<HTTPResult, Any, FormData, Any>(
        mutationFn = { element: FormData ->
            fetch(
                Config.uploadPath,
                jso {
                    method = "POST"
                    body = element
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        }
    )

    input {
        type = InputType.file
        onChange = {
            val formData = FormData().apply { append("files", it.target.files!![0]) }
            addMutation.mutate(formData, null)
        }
    }
}