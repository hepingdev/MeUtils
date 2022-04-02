package me.hp.meutils

import me.hp.meutils.utils.FileUtils;
import me.hp.meutils.extension.getMD5

/**
 * @author: HePing
 * @created: 2022/4/2
 * @desc:
 */

fun main(args: Array<String>){
    var name = "heping12345sdfsdf6789"

    var result1 = name.getMD5()

    var result2 = FileUtils.getMd5Value(name)

    println("result1:$result1 \nresult2:$result2")
}
