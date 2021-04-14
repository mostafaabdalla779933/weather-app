package com.example.weather.data.local.sharedpref

import com.example.weather.data.repos.RemoteRepo

class Repo {

    var data:ISharedprefer

    init {
        data=Sharedprefer
    }


    companion object{
        var repo: Repo? =null

        fun getInstance(): Repo? {
            if(repo ==null){
                synchronized (RemoteRepo::class.java){
                    if(repo ==null){
                        repo = Repo()
                    }
                }
            }
            return repo
        }
    }

    fun getLat():Float{
      return  data.getLat()
    }



   fun  getlng():Float{

       return data.getLng()
   }



}