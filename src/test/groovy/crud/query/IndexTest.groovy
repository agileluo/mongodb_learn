package crud.query;

import com.gmongo.GMongo

class IndexTest extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.inventory.remove([:])
		db.inventory.ensureIndex([type:1, name: 1]);
	}
	void test_explain(){
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'food', prices: 5, name: 'rice'])
		db.inventory << ([type: 'book', prices: 10, name: 'vgs'])
		db.inventory << ([type: 'it', prices: 21, name: 'csf'])
		
		println db.inventory.find([type:'food'], [type:1, _id:0]).explain()
	}
}
