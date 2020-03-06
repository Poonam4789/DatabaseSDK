package com.notification.group.demo.kdatabasesdk

data class QueryInfo(var id: String, var type: Int, var text: String, var version: Int) {

    private var _id: String = id
    private var _type: Int = type
    private var _text: String = text
    private var _version: Int = version

    constructor(id: String, type: Int, text: String) : this(id, type, text, 1) {
        _id = id
        _type = type
        _text = text
        _version = version
    }
}