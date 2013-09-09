package crud.create;

import com.gmongo.GMongo

class FirstInsert extends GroovyTestCase {
	def db;
	void setUp(){
		def mongo = new GMongo("127.0.0.1", 27017)
		db = mongo.getDB("learn")
		db.bios.remove([:])
	}
	void testFirstInsert(){
		db.bios.insert(
			[
				_id: 1,
				name: [
					first: 'John', last: 'Backus'
				],
				birth: new Date('Dec 03, 1924'),
				death: new Date('Mar 17, 2007'),
				contribs: [ 'Fortran', 'ALGOL', 'Backus-Naur Form', 'FP' ],
				awards: [ [
						award: 'W.W. McDowell Award',
						year: 1967,
						by: 'IEEE Computer Society'
					], [
						award: 'National Medal of Science',
						year: 1975,
					]
					, [
						award: 'Turing Award',
						year: 1977,
						by: 'ACM'
					], [
						award: 'Draper Prize',
						year: 1993,
						by: 'National Academy of Engineering'
					]
				]
			]
		)
		println db.bios.findOne().toString()
	}
}
