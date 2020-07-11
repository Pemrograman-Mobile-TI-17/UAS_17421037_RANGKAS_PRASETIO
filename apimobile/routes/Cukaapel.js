const multer = require('multer')
const fs = require('fs')
const router = require('express').Router()
const cuka = require('../controller/Cukaapel')

var storage = multer.diskStorage({
    filename: function (req, file, cb) {
        let ext = file.originalname.substring(
            file.originalname.lastIndexOf("."),
            file.originalname.length
        )
        cb(null, Date.now() + ext);
    },
    destination: function (req, file, cb){
        cb(null, './gambar')
    }
})

var upload = multer({storage: storage}).single("gambar")

router.post("/input", upload, (req, res) => {

    cuka.inputDataCuka(req.body, req.file.filename)
        .then((result)=> res.json(result))
        .catch((err)=> res.json(err))

})

router.get("/datacuka", (req, res) =>{
    cuka.lihatDataCuka()
        .then((result)=> res.json(result))
        .catch((err)=> res.json(err))
})

router.get("/datacuka/:id", (req, res) =>{
    cuka.lihatDetailDataCuka(req.params.id)
        .then((result)=> res.json(result))
        .catch((err)=> res.json(err))
})

router.delete("/hapus/:id", (req, res) =>{
    cuka.hapuscuka(req.params.id)
        .then((result)=> res.json(result))
        .catch((err)=> res.json(err))
})

router.put("/ubah/:id", upload, (req, res) =>{
    let fileName;
    if(req.body.gambar){
        fileName = req.body.gambar;
    }else{
        fileName = req.file.filename;
    }
    cuka.updatecuka(req.params.id, req.body, fileName)
        .then((result)=> res.json(result))
        .catch((err)=> res.json(err))
})

module.exports = router