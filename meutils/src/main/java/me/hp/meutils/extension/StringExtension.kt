package me.hp.meutils.extension

import me.hp.meutils.utils.LogUtils

/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: 字符串扩展类
 */

/**
 * 字符串是否为空
 */
fun String?.isBlank(): Boolean {
    return this == null || this.trim().isEmpty();
}

/**
 * 对指定长度字符重编码
 */
fun String?.recode(length: Int, encode: String): String? {
    if (this.isBlank()) return null
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
