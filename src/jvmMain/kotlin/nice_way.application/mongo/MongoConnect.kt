package nice_way.application.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import item.Item
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

val client = KMongo.createClient("mongodb://194.67.68.117:27017")
val database: MongoDatabase = client.getDatabase("test")
val collection: MongoCollection<Item> = database.getCollection()