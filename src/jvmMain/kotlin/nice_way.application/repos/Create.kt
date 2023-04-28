package nice_way.application.repos

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import data.BibTex
import item.Item
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.setValue
import java.util.*

fun MongoCollection<Item>.create(bib: BibTex){
    val item = Item(
        _id = UUID.randomUUID().toString(),
        elem = bib
    )
    this.insertOne(item)
}

fun MongoCollection<Item>.update(id: String, bib: BibTex){
    this.updateOne(Item::_id eq id, setValue(Item::elem, bib))
}

//fun MongoCollection<Item>.read(ids: Array<String>): FindIterable<Item> {
//    return this.find(Item::_id eq ids)
//}

fun MongoCollection<Item>.read(): FindIterable<Item> {
    return this.find()
}

fun MongoCollection<Item>.read(id: String): Item? {
    return this.findOne(Item::_id eq id)
}