package crud.update;

import com.gmongo.GMongo

class UpdateAField extends GroovyTestCase {
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
	void testUpdateAField(){
		db.bios.update([_id: 1], [$set: [name: 'agileChina']])
		assert db.bios.findOne([_id: 1]).name == 'agileChina'
	}
	void testAddNewField(){
		db.bios.update([_id: 1], [$set: [name: 'agileChina', 'love.love': 'agile']])
		assert db.bios.findOne([_id: 1]).love.love == 'agile'
	}
	void testRemoveField(){
		db.bios.update([_id: 1], [$unset: [title: 1]])
		assert db.bios.findOne([_id: 1]).title == null
	}
}
