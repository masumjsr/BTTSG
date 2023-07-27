package com.mssoftinc.bttsg.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.masum.datastore.Config
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferencesSerializer @Inject constructor() : Serializer<Config>{
    override val defaultValue: Config = Config.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Config =
        try{
            Config.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: Config, output: OutputStream) {
        t.writeTo(output)
    }
}