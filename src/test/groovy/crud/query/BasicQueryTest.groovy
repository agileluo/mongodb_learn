package crud.query;

import static org.junit.Assert.*

import com.gmongo.GMongo

class BasicQueryTest extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.inventory.remove([:])
	}
	void testBasicQuery(){
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'food', prices: 10, name: 'vgs'])
		db.inventory << ([type: 'food', prices: 21, name: 'csf'])
		db.inventory << ([type: 'book', prices: 89.00d, name: 'continuous Delivery'])
		db.inventory << ([type: 'book', prices: 69.00d, name: 'Refact'])
		db.inventory << ([type: 'book', prices: 52, name: 'effective java'])
		
		assert db.inventory.find([type: 'food', prices: [$lt: 8]])[0].name == 'rice'
		assert db.inventory.findOne([type: 'food', prices: [$lt: 8]], [type: 1]).name == null
		assert db.inventory.find([type: 'food']).count() == 3
		assert db.inventory.find([type: [$in: ['food', 'book']]]).count() == 6
		
		assert db.inventory.find([$or: [
				[type: 'book'],
				[name: 'rice']]
		]).count() == 4
		
		assert db.inventory.find([type: 'food', $or: [
				[prices: [$lt: 20]],
				[name: 'csf']]
		]).count() == 3
	}
	
	void testQuerySubDocument(){
		db.inventory << ([type: 'company', name: 'tencent',  address: [city: 'sz', street:'1008']])
		db.inventory << ([type: 'company', name: 'baidu',  address: [city: 'bj', street:'100']])
		
		assert db.inventory.find([address:[city: 'sz', street: '1008']])[0].name == 'tencent'
		assert db.inventory.find([address:[city: 'sz']]).count() == 0
		assert db.inventory.find(['address.city': 'sz'])[0].name == 'tencent'
	}
	void testQueryArray(){
		db.inventory << ([type: 'hobbits', content: ['java', 'groovy', 'js'], name: 'agileluo'])
		db.inventory << ([type: 'hobbits', content: ['java'], name: 'cmmi'])
		db.inventory << ([type: 'hobbits', content: ['scala', 'ruby'], name: 'xp'])
		db.inventory << ([type: 'hobbits', content: ['ruby', 'scala'], name: 'up'])
		
		assert db.inventory.find([content: ['java', 'groovy', 'js']]).count() == 1
		assert db.inventory.findOne([content: ['java', 'groovy', 'js']]).name == 'agileluo'
		assert db.inventory.find([content: 'ruby']).count() == 2
		assert db.inventory.find(['content.0': 'ruby'])[0].name == 'up'
	
	}
	void testQuerySubDocumentWithArray(){
		db.inventory << ([type: 'address', children: [
			[city: 'sz', zipcode: 58001],
			[city: 'beijing', zipcode: 10001],
			[city: 'toky', zipcode: 421]
		], name: 'china'])
		db.inventory << ([type: 'address', children: [
				[city: 'toky', zipcode: 999]
			], name: 'janpan'])
		
		assert db.inventory.find(['children.0.city': 'sz']).count() == 1
		assert db.inventory.find(['children.0.city': 'sz'])[0].name == 'china'
		assert db.inventory.find(['children.city': 'toky']).count() == 2
		
		assert db.inventory.find(['children.city': 'sz', 'children.zipcode' : 421]).count() == 1
		assert db.inventory.find([$elemMatch: [city:'sz', zipcode: 421]]).count() == 0
	}
	
	void testResultProjection(){
		db.inventory << ([type: 'food', prices: 5, name: 'rice']);
		
		assert db.inventory.findOne().name == 'rice'
		assert db.inventory.findOne([:], [type:1]).name == null
		assert db.inventory.findOne([:], [prices: 0]).name == 'rice'
		assert db.inventory.findOne([:], [prices: 0]).prices == null
		assert db.inventory.findOne([:], [prices: 0]).type == 'food'
	}
}
