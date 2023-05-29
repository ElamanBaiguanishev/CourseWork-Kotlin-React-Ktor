package components.find

import config.Config
import data.BibTex
import tools.fetchText
import query.QueryError
import kotlinx.serialization.decodeFromString
import react.FC
import react.Props
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import kotlinx.serialization.json.Json
import tanstack.react.query.UseBaseQueryResult

external interface FindProps : Props {
    var component: FC<ShowProps>
    var criterions: List<String>
    var keys: List<Pair<String, QueryKey>>
}

val CFindCriterion = FC<FindProps>("Find") { props ->
    val queries = mutableListOf<UseBaseQueryResult<String, QueryError>>()

    props.keys.forEachIndexed { index, (param, key) ->
        queries.add(
            useQuery<String, QueryError, String, QueryKey>(
                queryKey = key,
                queryFn = {
                    fetchText("${Config.readPath}$param:${props.criterions[index]}")
                }
            )
        )
    }
    val files: MutableList<List<BibTex>> = mutableListOf()
    for (query in queries) {
        if (query.isLoading) {
            +" "
        } else if (query.isError) {
            +"Error"
        } else {
            files.add(Json.decodeFromString<List<BibTex>>(query.data ?: ""))
        }
    }

    props.component {
        this.files = files
        this.professorName = props.criterions[0]
        this.name = props.criterions[1]
        this.zavName = props.criterions[2]
        this.secName = props.criterions[3]
    }
}