package crud.delete;

import com.gmongo.GMongo

class delete extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.bios.remove([:])
		db.bios.insert([
			[	_id: 1,
				name: 'agileluo',
			], [
				_id: 2,
				name: 'uubox'
			],
			[
				_id: 3,
				name: 'agileChina'
			]
		])
	}
	void testDelete(){
		db.bios.remove([name: ~/^a/])
		assert db.bios.find().count() == 1
	}
	/**
	 * not support
	 */
	/*void testDeleteSingle(){
		db.bios.remove([name: ~/^a/], 1)
		assert db.bios.find().count() == 2
	}*/
}
