package crud.update;

import com.gmongo.GMongo

class UpdateWithUpsert extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.bios.remove([:])
		db.bios.insert([
			[	_id: 1,
				name: 'agileluo',
				title: 'java',
				age: 24,
				love: [
					name: 'cc',
					age : 23
					]
			], [
				_id: 2,
				name: 'uubox'
			]
		])
	}
	void testUpdateWithUpsert(){
		db.bios.update([_id: 3], [$set: [name: 'agileChina']], true)
		assert db.bios.findOne([_id: 3])._id == 3
	}
	void testSave_with_Id(){
		db.bios.save([_id: 3, name: 'agileChina'])
		assert db.bios.findOne([_id: 3])._id == 3
	}
	void testSave_without_id(){
		db.bios.save([name: 'agileChina'])
		assert db.bios.find().size() == 3
	}
}
