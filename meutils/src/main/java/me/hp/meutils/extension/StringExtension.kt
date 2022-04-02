package me.hp.meutils.extension

import me.hp.meutils.utils.LogUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author: HePing
 * @created: 2022/4/2
 * @desc: String扩展函数
 */

/**
 * 字符串是否为空
 */
fun String?.isEmpty(): Boolean {
    return this == null || this.trim().length == 0;
}

/**
 * 对指定长度字符重编码
 */
fun String?.recode(length: Int, encode: String): String? {
    if (this.isEmpty()) return null
    try {
        val sb = StringBuilder()
        var currentLength = 0
        for (c in this!!.toCharArray()) {
            currentLength += c.toString().toByteArray(charset(encode!!)).size
            if (currentLength > length) break
            sb.append(c)
        }
        return sb.toString()
    } catch (e: Exception) {
        LogUtils.e("StringExtension", "recode error.")
    }
    return null
}

/**
 * 获取MD5值
 */
fun String?.getMD5(): String? {
    if (this.isEmpty()) return null

    val sb = StringBuffer()
    // 得到一个信息摘要器
    try {
        val digest = MessageDigest.getInstance("md5")
        val result = digest.digest(this!!.toByteArray())
        // 把每一个byte做一个与运算 0xff
        for (b in result) {
            // 与运算
            val number: Int = b.toInt() and 0xff
            val str = Integer.toHexString(number)
            if (str.length == 1) {
                sb.append("0")
            }
            sb.append(str)
        }
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        //清空
        sb.setLength(0)
    }
    return sb.toString()
}


