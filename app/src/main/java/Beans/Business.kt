package Beans

class Business{

    var id: Int = 0
    var name: String
    var description: String
    var address: String
    var city: String
    var expertise: String
    var image: String
    var remodelerId: Int

    constructor(name: String, description: String, address: String, city: String, expertise: String, image: String, remodelerId: Int) {
        this.name = name
        this.description = description
        this.address = address
        this.city = city
        this.expertise = expertise
        this.image = image
        this.remodelerId = remodelerId
    }
}