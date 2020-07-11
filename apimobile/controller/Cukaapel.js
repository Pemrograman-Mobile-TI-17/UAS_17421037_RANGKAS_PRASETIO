const cuka = require('../model/Cukaapel.js')
const response = require('../config/response')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId
exports.inputDataCuka = (data, gambar) =>
    new Promise(async (resolve, reject) =>{

        const CukaBaru = new cuka({
            kodecuka : data.kodecuka,
            ukuran : data.ukuran,
            harga: data.harga,
            gambar: gambar
        })

        await cuka.findOne({kodecuka: data.kodecuka})
            .then(cuka =>{
                if (cuka){
                    reject(response.commonErrorMsg('Kode Cuka Sudah Digunakan'))
                }else{
                    CukaBaru.save()
                        .then(r =>{
                            resolve(response.commonSuccessMsg('Berhasil Menginput Data'))
                        }).catch(err =>{
                        reject(response.commonErrorMsg('Mohon Maaf Input Cuka Gagal'))
                    })
                }
            }).catch(err =>{
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
            })
    })

exports.lihatDataCuka = () =>
    new Promise(async (resolve, reject) =>{
        await cuka.find({})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.lihatDetailDataCuka = (kodecuka) =>
    new Promise(async (resolve, reject) =>{
        await cuka.findOne({kodecuka: kodecuka})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.updatecuka = (id, data, gambar) =>
    new Promise(async (resolve, reject)=>{
        await cuka.updateOne(
            {_id : ObjectId(id)},
            {
                $set: {
                    kodecuka : data.kodecuka,
                    ukuran : data.ukuran,
                    harga: data.harga,
                    gambar: gambar
                }
            }
        ).then(cuka =>{
            resolve(response.commonSuccessMsg('Berhasil Mengubah Data'))
        }).catch(err =>{
            reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
        })
    })

exports.hapuscuka = (_id) =>
    new Promise(async (resolve, reject) =>{
        await cuka.remove({_id: ObjectId(_id)})
            .then(() =>{
                resolve(response.commonSuccessMsg('Berhasil Menghapus Data'))
            }).catch(() =>{
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
            })
    })