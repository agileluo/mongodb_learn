package crud.query;

import com.gmongo.GMongo

class CursorTest extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.inventory.remove([:])
	}
	void test_explain(){
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'book', prices: 10, name: 'vgs'])
		db.inventory << ([type: 'it', prices: 21, name: 'csf'])
		
		assert db.inventory.find([type:'food'])[2].name == 'rice'
		assert db.inventory.find([type:'food']).toArray()[2].name == 'rice'
	}
}
