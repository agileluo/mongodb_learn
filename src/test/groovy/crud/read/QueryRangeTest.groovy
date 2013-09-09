package crud.read;

import com.gmongo.GMongo

class QueryRangeTest extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.students.remove([:])
	}
	
	void testQueryRange(){
		db.students << ([name: 'agile',  scores: [80, 100]])
		db.students << ([name: 'agileChina',  scores: [-10, 30]])
		db.students << ([name: 'agileluo',  scores: [50, 80]])
		
		db.students.find([scores: [$gt: 0, $lt: 80]], [name: 1, scores:[$slice: 1]])
			.sort([name:-1]).each {
			println it.name + it.scores
		}
	}
	void testModifyCursor(){
		db.students << ([name: 'agile',  age:3])
		db.students << ([name: 'ruby',  age:2])
		db.students << ([name: 'java',  age:11])
		db.students << ([name: 'groovy',  age:3])
		db.students << ([name: 'c++',  age:12])
		db.students << ([name: 'js',  age:15])
		db.students << ([name: 'node.js',  age:1])
		db.students << ([name: 'scala',  age:1])
		
		db.students.find().sort([name: 1]).skip(3).limit(3).each {
			println it.name + ', ' + it.age
		}
	}
	
	
}
