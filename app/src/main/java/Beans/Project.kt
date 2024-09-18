package Beans

import java.sql.Date

class Project {

    var id: Int = 0
    var name: String
    var description: String
    var business_id: Int
    var contractor_id: Int
    var start_date: Date
    var finish_date: Date
    var image: String

    constructor(name: String, description: String, business_id: Int, contractor_id: Int, start_date: Date, finish_date: Date, image: String) {
        this.name = name
        this.description = description
        this.business_id = business_id
        this.contractor_id = contractor_id
        this.start_date = start_date
        this.finish_date = finish_date
        this.image = image
    }
}