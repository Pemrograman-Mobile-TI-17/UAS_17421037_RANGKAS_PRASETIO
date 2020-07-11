const mongoose = require('mongoose');

const userSchema = mongoose.Schema({

    kodecuka: {
        type: String
    },
    ukuran: {
        type: String
    },
    harga: {
        type: String
    },
    gambar: {
        type: String
    }
})
module.exports = mongoose.model('cuka', userSchema)