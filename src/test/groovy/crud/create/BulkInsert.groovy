package crud.create;

import com.gmongo.GMongo

class BulkInsert extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.bios.remove([:])
	}
	void testBulkInsert(){
		db.bios.insert([
			[	name: 'agileluo',
				title: 'java',
				age: 24
			], [
				name: 'uubox'
			], [
				name: 'hello',
				age: 22
			]	
		])
		assert 3 == db.bios.find().size()
	}
}
