package nice_way.application.mongo

import com.mongodb.reactivestreams.client.MongoDatabase
import data.BibTex
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollection

val client = KMongo.createClient("mongodb://194.67.68.117:27017")
val database: MongoDatabase = client.getDatabase("test")
val collection = database.getCollection<BibTex>()