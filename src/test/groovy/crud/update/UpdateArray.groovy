package crud.update;

import com.gmongo.GMongo

class UpdateArray extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.bios.remove([:])
		db.bios.insert([
			[	_id: 1,
				name: 'agileluo',
				like: ['java', 'js', 'running']
			], [
				_id: 2,
				name: 'uubox',
				subject: [
					[ name: 'math',score: 87 ],
				  	[ name: 'chinese', score: 70]
					]
			]
		])
	}
	void testUpdateArrayByPosition(){
		db.bios.update([_id: 1], [$set: ['like.0': 'j2ee']]);
		assert db.bios.findOne([_id: 1]).like[0] == 'j2ee'
	}
	void testUpdateArrayWithOutPosition(){
		db.bios.update([_id: 1, like: 'java'], [$set: ['like.$': 'j2ee']]);
		assert db.bios.findOne([_id: 1]).like[0] == 'j2ee'
	}
	void testUpdateSubDocumentArrayWithOutPosition(){
		db.bios.update([_id: 2, 'subject.name': 'math'], [$set: ['subject.$.name': 'j2ee']]);
		assert db.bios.findOne([_id: 2]).subject[0].name == 'j2ee'
	}
	void testAddAnElementToArray(){
		db.bios.update([_id: 1], [$push:[like: 'ruby']]);
		db.bios.findOne([_id: 1]).like[3] == 'ruby'
	}
	
}
