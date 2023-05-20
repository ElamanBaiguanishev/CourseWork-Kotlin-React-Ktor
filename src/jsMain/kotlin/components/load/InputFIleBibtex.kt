package components.load

import config.Config
import data.BibTex
import js.core.get
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import web.html.InputType
import kotlin.js.json


val CInputFileBibtex = FC<Props>("inputText") {
    val queryClient = useQueryClient()
    val myQueryKey = arrayOf("update").unsafeCast<QueryKey>()

    var result by useState<String>(" ")
    val fields = Regex("([a-zA-Z]+) *= *[\"{]*([a-zA-Z0-9 ,-:]+)[\"}]*")
        .findAll(result)
        .associate {
            it.groups[1]?.value to it.groups[2]?.value
        }.toMutableMap()

    val params = Regex("@([a-zA-Z]+) *\\{ *(.+),")
        .findAll(result)
        .map {
            it.groups.map { it?.value }
        }.toList().flatten()

    div {
        input {
            type = InputType.file
            onChange = { changeEvent ->
                changeEvent.target.files!![0].text().then { result = it }
                changeEvent.target.value = ""
            }
        }
    }

    val addMutation = useMutation<HTTPResult, Any, BibTex, Any>(
        mutationFn = { element: BibTex ->
            fetch(
                url = Config.uploadPath,
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(element)
                })
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        }
    )

    if (fields.isNotEmpty()) {
        CEditInputFIle {
            fields += "type" to params[0]
            fields += "tag" to params[1]
            this.fields = fields.toMutableMap()
            this.query = {
                addMutation.mutateAsync(it, null)
            }
        }
    }
}