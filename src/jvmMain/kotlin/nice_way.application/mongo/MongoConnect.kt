package nice_way.application.mongo

import com.mongodb.reactivestreams.client.MongoDatabase
import data.BibTex
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollection

val client = KMongo.createClient("mongodb://root:password@192.168.0.120:27017")
val database: MongoDatabase = client.getDatabase("test")
val collection = database.getCollection<BibTex>()